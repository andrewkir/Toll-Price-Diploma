package ru.andrewkir.feature.profile.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIEvent


sealed class ProfileUIEvent: UIEvent {
    data object OnAuthClicked : ProfileUIEvent()
    data object OnScreenOpened : ProfileUIEvent()
    data object OnLogoutClicked : ProfileUIEvent()
}