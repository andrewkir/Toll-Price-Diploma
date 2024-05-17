package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.register.RegisterUIEvent
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.register.RegisterUIState

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    uiState: RegisterUIState,
    onEvent: (RegisterUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(horizontal = 30.dp)
                .padding(contentPadding)
        ) {
            LoginField(
                value = uiState.email,
                onChange = { value -> onEvent(RegisterUIEvent.OnEmailChanged(value)) },
                modifier = Modifier.fillMaxWidth()
            )

            LoginField(
                label = "Логин",
                placeholder = "Введите логин",
                value = uiState.username,
                onChange = { value -> onEvent(RegisterUIEvent.OnUsernameChanged(value)) },
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                value = uiState.password,
                onChange = { value -> onEvent(RegisterUIEvent.OnPasswordChanged(value)) },
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                label = "Повторите пароль",
                placeholder = "Подтвердите пароль",
                value = uiState.passwordConfirmation,
                onChange = { value -> onEvent(RegisterUIEvent.OnPasswordConfirmationChanged(value)) },
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                onClick = { onEvent(RegisterUIEvent.SubmitClicked) },
                enabled = uiState.isButtonEnabled,
                shape = RoundedCornerShape(15.dp),
            ) {
                Text("Регистрация")
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onEvent(RegisterUIEvent.OpenLoginScreen) }
                    .padding(6.dp)
            ) {
                Text(text = "Уже есть аккаунт? Войти")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRegisterScreen() {
    RegisterScreenContent(uiState = RegisterUIState(), onEvent = {}, snackbarHostState = SnackbarHostState())
}