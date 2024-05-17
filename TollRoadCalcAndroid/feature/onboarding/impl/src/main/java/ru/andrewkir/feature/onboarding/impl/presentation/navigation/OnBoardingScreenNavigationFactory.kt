package ru.andrewkir.feature.onboarding.impl.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import ru.andrewkir.feature.onboarding.api.OnBoardingScreenFeature
import ru.andrewkir.feature.onboarding.impl.presentation.screen.OnBoardingScreen
import ru.andrewkir.feature.onboarding.impl.presentation.viewmodel.OnBoardingViewModel
import javax.inject.Inject

@HiltComposeNavigationFactory
class OnBoardingScreenNavigationFactory @Inject constructor() : ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navHostController: NavHostController) {
        builder.viewModelComposable<OnBoardingViewModel>(
            route = OnBoardingScreenFeature.ROUTE_NAME,
            content = {
                OnBoardingScreen(viewModel = this)
            }
        )
    }
}