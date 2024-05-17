package ru.andrewkir.feature.profile.impl.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import ru.andrewkir.feature.profile.api.ProfileFeature
import ru.andrewkir.feature.profile.impl.presentation.screen.ProfileScreen
import ru.andrewkir.feature.profile.impl.presentation.viewmodel.ProfileViewModel
import javax.inject.Inject

@HiltComposeNavigationFactory
class ProfileScreenNavigationFactory @Inject constructor() : ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navHostController: NavHostController) {
        builder.viewModelComposable<ProfileViewModel>(
            route = ProfileFeature.ROUTE_NAME,
            content = {
                ProfileScreen(viewModel = this, navController = navHostController)
            }
        )
    }
}