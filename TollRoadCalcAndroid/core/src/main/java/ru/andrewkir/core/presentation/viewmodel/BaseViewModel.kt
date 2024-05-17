package ru.andrewkir.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.andrewkir.core.domain.models.NetworkResult
import ru.andrewkir.core.presentation.contract.UIEffect
import ru.andrewkir.core.presentation.contract.UIEvent
import ru.andrewkir.core.presentation.contract.UIState
import java.io.IOException

abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect>(
    initialState: State
) : ViewModel() {

    private val mUIState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = mUIState.asStateFlow()

    val currentState: State
        get() = uiState.value

    private val event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val mUIEffect: Channel<Effect> = Channel()
    val effect = mUIEffect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    protected abstract fun handleEvent(event: Event)

    private fun subscribeToEvents() {
        viewModelScope.launch {
            event.collect(::handleEvent)
        }
    }

    fun setEvent(newEvent: Event) {
        viewModelScope.launch {
            event.emit(newEvent)
        }
    }

    fun setEffect(builder: () -> Effect) {
        val effectVal = builder()
        viewModelScope.launch {
            mUIEffect.send(effectVal)
        }
    }

    fun setEffect(effect: Effect) =
        setEffect { effect }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        mUIState.value = newState
    }

    protected suspend fun <T> executeRequest(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> T,
    ): NetworkResult<T> = withContext(dispatcher) {
        safeApiCall(
            call
        )
    }


    companion object {

        suspend fun <T> safeApiCall(
            apiCall: suspend () -> T,
        ): NetworkResult<T> {
            return try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> NetworkResult.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        NetworkResult.GenericError(code, errorResponse)
                    }

                    else -> {
                        NetworkResult.GenericError(null, null)
                    }
                }
            }
        }

        private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
            return try {
                throwable.response()?.errorBody()?.string()?.let {
                    Gson().fromJson(it, ErrorResponse::class.java)
                }
            } catch (exception: Exception) {
                null
            }
        }
    }
}

data class ErrorResponse(
    val detail: String,
)