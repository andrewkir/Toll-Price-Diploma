package ru.andrewkir.feature.profile.impl.presentation.components.content

import androidx.appcompat.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIEvent
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIState

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    onEvent: (ProfileUIEvent) -> Unit,
    uiState: ProfileUIState,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        if (!uiState.isUserAuthorized) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Button(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 16.dp),
                    onClick = { onEvent(ProfileUIEvent.OnAuthClicked) },
                    enabled = true,
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text("Авторизация")
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Spacer(modifier = Modifier.height(64.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(64.dp),
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "User avatar"
                    )

                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = uiState.username ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = uiState.email ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorResource(id = R.color.material_blue_grey_800)
                        )
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            onClick = { onEvent(ProfileUIEvent.OnLogoutClicked) },
                            enabled = true,
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            Text("Выйти")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    ProfileScreenContent(
        modifier = Modifier.fillMaxSize(),
        uiState = ProfileUIState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
    )
}