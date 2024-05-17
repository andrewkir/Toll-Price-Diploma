package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.register

import android.util.Patterns
import ru.andrewkir.core.presentation.contract.UIState
import java.util.regex.Pattern


data class RegisterUIState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
) : UIState {

    val isButtonEnabled = email.isNotBlank()
            && username.isNotBlank()
            && password.isNotBlank()
            && passwordConfirmation.isNotBlank()
            && password == passwordConfirmation
            && isValidEmail(email)

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}