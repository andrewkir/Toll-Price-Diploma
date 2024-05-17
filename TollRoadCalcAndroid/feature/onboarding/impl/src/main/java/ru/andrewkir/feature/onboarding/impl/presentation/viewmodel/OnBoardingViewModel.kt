package ru.andrewkir.feature.onboarding.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.andrewkir.core.presentation.viewmodel.BaseViewModel
import ru.andrewkir.feature.onboarding.impl.domain.usecases.AppEntryUseCases
import ru.andrewkir.feature.onboarding.impl.presentation.contract.OnBoardingUIEffect
import ru.andrewkir.feature.onboarding.impl.presentation.contract.OnBoardingUIEvent
import ru.andrewkir.feature.onboarding.impl.presentation.contract.OnBoardingUIState
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): BaseViewModel<OnBoardingUIEvent, OnBoardingUIState, OnBoardingUIEffect>(
    OnBoardingUIState()
) {

    override fun handleEvent(event: OnBoardingUIEvent) {
        when(event){
            is OnBoardingUIEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry(){
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }
}