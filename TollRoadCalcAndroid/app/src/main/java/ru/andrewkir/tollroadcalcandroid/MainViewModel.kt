package ru.andrewkir.tollroadcalcandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.andrewkir.feature.mainscreen.api.MainScreenFeature
import ru.andrewkir.feature.onboarding.api.OnBoardingScreenFeature
import ru.andrewkir.feature.onboarding.impl.domain.usecases.AppEntryUseCases
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
): ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(OnBoardingScreenFeature.ROUTE_NAME)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach {shouldStartFromHomeScreen ->
            startDestination = if(shouldStartFromHomeScreen){
                MainScreenFeature.ROUTE_NAME
            } else {
                OnBoardingScreenFeature.ROUTE_NAME
            }

            delay(300)

            splashCondition = false
        }.launchIn(viewModelScope)
    }
}