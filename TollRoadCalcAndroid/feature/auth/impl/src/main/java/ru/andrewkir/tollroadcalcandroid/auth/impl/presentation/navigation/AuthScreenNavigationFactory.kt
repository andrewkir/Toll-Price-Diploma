package ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import ru.andrewkir.feature.profile.impl.presentation.screen.LoginScreen
import ru.andrewkir.feature.profile.impl.presentation.screen.RegisterScreen
import ru.andrewkir.tollroadcalcandroid.auth.api.AuthScreenFeature
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.viewmodel.LoginViewModel
import ru.andrewkir.tollroadcalcandroid.auth.impl.presentation.viewmodel.RegisterViewModel
import javax.inject.Inject

@HiltComposeNavigationFactory
class AuthScreenNavigationFactory @Inject constructor(): ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<LoginViewModel>(
            route = AuthScreenFeature.LOGIN_ROUTE,
            content = {
                LoginScreen(viewModel = this, navController = navController)
            }
        )

        builder.viewModelComposable<RegisterViewModel>(
            route = AuthScreenFeature.REGISTER_ROUTE,
            content = {
                RegisterScreen(viewModel = this, navController = navController)
            }
        )
    }
}