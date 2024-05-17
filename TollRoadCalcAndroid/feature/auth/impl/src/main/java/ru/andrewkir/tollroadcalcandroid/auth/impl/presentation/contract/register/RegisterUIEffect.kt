package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.register

import ru.andrewkir.core.presentation.contract.UIEffect
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login.LoginUIEffect

sealed class RegisterUIEffect: UIEffect {
    data object OpenLoginScreen: RegisterUIEffect()
    data object GoBack: RegisterUIEffect()

    class ShowSnackbar(val message: String): RegisterUIEffect()
}