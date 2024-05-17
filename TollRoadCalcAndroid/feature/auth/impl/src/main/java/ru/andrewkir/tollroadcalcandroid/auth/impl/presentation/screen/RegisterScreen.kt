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
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.content.RegisterScreenContent
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.contract.register.RegisterUIEffect
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect: RegisterUIEffect ->
            uiEffectHandler(effect, navController, snackbarHostState)
        }
    }

    RegisterScreenContent(
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
    effect: RegisterUIEffect,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    when(effect){
        RegisterUIEffect.OpenLoginScreen ->
            AuthScreenFeature.openLoginScreen(navController)

        RegisterUIEffect.GoBack ->
            navController.popBackStack()

        is RegisterUIEffect.ShowSnackbar ->
            snackbarHostState.showSnackbar(effect.message)
    }
}