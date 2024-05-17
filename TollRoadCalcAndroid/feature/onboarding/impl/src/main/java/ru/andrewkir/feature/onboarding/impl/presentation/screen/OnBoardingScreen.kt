package ru.andrewkir.feature.onboarding.impl.presentation.screen

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import ru.andrewkir.feature.onboarding.impl.presentation.components.content.OnBoardingScreenContent
import ru.andrewkir.feature.onboarding.impl.presentation.contract.OnBoardingUIEffect
import ru.andrewkir.feature.onboarding.impl.presentation.viewmodel.OnBoardingViewModel


@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect: OnBoardingUIEffect ->
            uiEffectHandler(effect)
        }
    }

    OnBoardingScreenContent(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        onEvent = viewModel::setEvent
    )
}

private fun uiEffectHandler(
    effect: OnBoardingUIEffect
) {

}