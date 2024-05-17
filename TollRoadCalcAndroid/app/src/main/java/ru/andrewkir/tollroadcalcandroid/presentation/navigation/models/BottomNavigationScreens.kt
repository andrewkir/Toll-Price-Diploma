package ru.andrewkir.tollroadcalcandroid.presentation.navigation.models

import ru.andrewkir.feature.mainscreen.api.MainScreenFeature
import ru.andrewkir.feature.onboarding.api.OnBoardingScreenFeature
import ru.andrewkir.feature.profile.api.ProfileFeature

sealed class BottomNavigationScreens(val route : String) {
    object Home : BottomNavigationScreens(MainScreenFeature.ROUTE_NAME)
    object Search : BottomNavigationScreens(OnBoardingScreenFeature.ROUTE_NAME)
    object Profile : BottomNavigationScreens(ProfileFeature.ROUTE_NAME)
}