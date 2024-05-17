package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login

import ru.andrewkir.core.presentation.contract.UIEffect

sealed class LoginUIEffect: UIEffect {
    data object OpenRegisterScreen: LoginUIEffect()
    data object GoBack: LoginUIEffect()

    class ShowSnackbar(val message: String): LoginUIEffect()
}