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
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login.LoginUIEvent
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login.LoginUIState

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUIState,
    onEvent: (LoginUIEvent) -> Unit,
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
                value = uiState.emailOrUsername,
                onChange = { value -> onEvent(LoginUIEvent.OnEmailOrUsernameChanged(value)) },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = uiState.password,
                onChange = { value -> onEvent(LoginUIEvent.OnPasswordChanged(value)) },
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = { onEvent(LoginUIEvent.SubmitClicked) },
                enabled = uiState.isButtonEnabled,
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("Войти")
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onEvent(LoginUIEvent.OpenRegisterScreen) }
                    .padding(6.dp)
            ) {
                Text(text = "Еще нет аккаунта? Зарегистрироваться")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    LoginScreenContent(
        uiState = LoginUIState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}