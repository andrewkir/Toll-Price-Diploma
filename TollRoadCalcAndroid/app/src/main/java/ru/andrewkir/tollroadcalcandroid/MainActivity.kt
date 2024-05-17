package ru.andrewkir.tollroadcalcandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.lachlanmckee.hilt.compose.navigation.factory.addNavigation
import net.lachlanmckee.hilt.compose.navigation.factory.addNavigationFactoriesNavigation
import net.lachlanmckee.hilt.compose.navigation.factory.hiltNavGraphNavigationFactories
import ru.andrewkir.feature.mainscreen.api.MainScreenFeature
import ru.andrewkir.feature.onboarding.api.OnBoardingScreenFeature
import ru.andrewkir.feature.profile.api.ProfileFeature
import ru.andrewkir.tollroadcalcandroid.presentation.navigation.models.BottomNavigationItem
import ru.andrewkir.tollroadcalcandroid.ui.theme.TollRoadCalcAndroidTheme
import ru.dgis.sdk.Context
import ru.dgis.sdk.positioning.DefaultLocationSource
import ru.dgis.sdk.positioning.registerPlatformLocationSource
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) -> {
                    // Precise location access granted.
                }

                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    // Only approximate location access granted.
                }

                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )


        val locationSource = DefaultLocationSource(applicationContext)
        registerPlatformLocationSource(context, locationSource)

        setContent {
            TollRoadCalcAndroidTheme {

                val navController = rememberNavController()
                val context = LocalContext.current
                var navigationSelectedItem by remember {
                    mutableIntStateOf(0)
                }

                if (viewModel.startDestination == OnBoardingScreenFeature.ROUTE_NAME) {
                    NavHost(navController, startDestination = OnBoardingScreenFeature.ROUTE_NAME) {
                        hiltNavGraphNavigationFactories(context).addNavigation(this, navController)
                    }

                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background))
                } else {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        content = { padding ->
                            NavHost(
                                modifier = Modifier.padding(padding),
                                navController = navController,
                                startDestination = viewModel.startDestination
                            ) {
                                addNavigationFactoriesNavigation(context, navController)
                            }
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = navBackStackEntry.isBottomNavigationBarVisible(),
                                enter = slideInVertically(initialOffsetY = { it }),
                                exit = slideOutVertically(targetOffsetY = { it })
                            ) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ) {
                                    BottomNavigationItem().bottomNavigationItems()
                                        .forEachIndexed { index, navigationItem ->
                                            NavigationBarItem(
                                                selected = index == navigationSelectedItem,
                                                label = {
                                                    Text(navigationItem.label)
                                                },
                                                icon = {
                                                    Icon(
                                                        navigationItem.icon,
                                                        contentDescription = navigationItem.label
                                                    )
                                                },
                                                onClick = {
                                                    navigationSelectedItem = index
                                                    navController.navigate(navigationItem.route) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            )
                                        }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun NavBackStackEntry?.isBottomNavigationBarVisible(): Boolean {
    val screenVisible = listOf(
        MainScreenFeature.ROUTE_NAME,
        ProfileFeature.ROUTE_NAME,
    )
    return this?.destination?.route in screenVisible
}