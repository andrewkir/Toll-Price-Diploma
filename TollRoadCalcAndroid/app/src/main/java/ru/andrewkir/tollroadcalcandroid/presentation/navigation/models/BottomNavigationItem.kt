package ru.andrewkir.tollroadcalcandroid.presentation.navigation.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Карта",
                icon = Icons.Filled.Home,
                route = BottomNavigationScreens.Home.route
            ),
//            BottomNavigationItem(
//                label = "Search",
//                icon = Icons.Filled.Search,
//                route = BottomNavigationScreens.Search.route
//            ),
            BottomNavigationItem(
                label = "Профиль",
                icon = Icons.Filled.AccountCircle,
                route = BottomNavigationScreens.Profile.route
            ),
        )
    }
}