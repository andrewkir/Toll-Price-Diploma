package ru.andrewkir.tollroadcalcandroid.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavigationFactory {

    fun create(builder: NavGraphBuilder, navController: NavController, intent: Intent? = null)
}