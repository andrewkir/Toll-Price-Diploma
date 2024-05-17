package ru.andrewkir.feature.home.impl.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import ru.andrewkir.feature.home.impl.presentation.screen.HomeScreen
import ru.andrewkir.feature.home.impl.presentation.viewmodel.HomeScreenViewModel
import ru.andrewkir.feature.mainscreen.api.MainScreenFeature
import ru.dgis.sdk.Context
import javax.inject.Inject

@HiltComposeNavigationFactory
class HomeScreenNavigationFactory @Inject constructor(
    private val dGisContext: Context,
) : ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navHostController: NavHostController) {
        builder.viewModelComposable<HomeScreenViewModel>(
            route = MainScreenFeature.ROUTE_NAME,
            content = {
                HomeScreen(viewModel = this, dGisContext = dGisContext)
            }
        )
    }
}