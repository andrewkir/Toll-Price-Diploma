package ru.andrewkir.feature.onboarding.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIEvent


sealed class OnBoardingUIEvent: UIEvent {

    object SaveAppEntry: OnBoardingUIEvent()
}