package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login

import ru.andrewkir.core.presentation.contract.UIEvent


sealed class LoginUIEvent : UIEvent {
    class OnEmailOrUsernameChanged(val value: String): LoginUIEvent()
    class OnPasswordChanged(val password: String): LoginUIEvent()


    data object OpenRegisterScreen: LoginUIEvent()
    data object SubmitClicked: LoginUIEvent()
}