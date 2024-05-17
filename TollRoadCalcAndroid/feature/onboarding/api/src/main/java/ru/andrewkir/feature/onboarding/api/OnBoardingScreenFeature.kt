package ru.andrewkir.feature.onboarding.api

import androidx.navigation.NavController

object OnBoardingScreenFeature {

    const val ROUTE_NAME = "onBoardingScreen"

    fun openOnBoardingScreen(
        navController: NavController
    ) {
        navController.navigate(ROUTE_NAME)
    }
}