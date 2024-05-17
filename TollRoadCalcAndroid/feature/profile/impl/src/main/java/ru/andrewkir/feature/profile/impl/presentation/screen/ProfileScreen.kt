package ru.andrewkir.feature.profile.impl.presentation.screen

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
import ru.andrewkir.feature.profile.impl.presentation.components.content.ProfileScreenContent
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIEffect
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIEvent
import ru.andrewkir.feature.profile.impl.presentation.viewmodel.ProfileViewModel
import ru.andrewkir.tollroadcalcandroid.auth.api.AuthScreenFeature


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect: ProfileUIEffect ->
            uiEffectHandler(effect, navController, snackbarHostState)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileUIEvent.OnScreenOpened)
    }

    ProfileScreenContent(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        onEvent = viewModel::setEvent,
        uiState = state,
        snackbarHostState = snackbarHostState,
    )
}

private suspend fun uiEffectHandler(
    effect: ProfileUIEffect,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    when(effect){
        ProfileUIEffect.OpenAuthScreen ->
            AuthScreenFeature.openLoginScreen(navController)

        is ProfileUIEffect.ShowSnackbar ->
            snackbarHostState.showSnackbar(effect.message)
    }
}