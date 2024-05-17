package ru.andrewkir.feature.profile.impl.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ru.andrewkir.tollroadcalcandroid.auth.api.AuthScreenFeature
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.content.LoginScreenContent
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.login.LoginUIEffect
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect: LoginUIEffect ->
            uiEffectHandler(effect, navController, snackbarHostState)
        }
    }

    LoginScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        uiState = state,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}

private suspend fun uiEffectHandler(
    effect: LoginUIEffect,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    when(effect){
        LoginUIEffect.OpenRegisterScreen ->
            AuthScreenFeature.openRegisterScreen(navController)

        LoginUIEffect.GoBack ->
            navController.popBackStack()

        is LoginUIEffect.ShowSnackbar ->
            snackbarHostState.showSnackbar(effect.message)
    }
}