package ru.andrewkir.tollroadcalcandroid.auth.api

import androidx.navigation.NavController

object AuthScreenFeature {

    const val LOGIN_ROUTE = "loginScreenFeature"
    const val REGISTER_ROUTE = "registerScreenFeature"

    fun openLoginScreen(navController: NavController) =
        navController.navigate(LOGIN_ROUTE) {
            popUpTo(REGISTER_ROUTE) {
                inclusive = true
            }
        }

    fun openRegisterScreen(navController: NavController) =
        navController.navigate(REGISTER_ROUTE) {
            popUpTo(LOGIN_ROUTE) {
                inclusive = true
            }
        }
}