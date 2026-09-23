package com.thestackdigest.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.thestackdigest.app.R

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry
        ?.destination
        ?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Routes.FEED,
            onClick = {
                navigateTo(navController, Routes.FEED)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Feed"
                )
            },
            label = {
                Text("Feed")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SAVED,
            onClick = {
                navigateTo(navController, Routes.SAVED)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "Saved"
                )
            },
            label = {
                Text("Saved")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SETTINGS,
            onClick = {
                navigateTo(navController, Routes.SETTINGS)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            },
            label = {
                Text("Settings")
            }
        )
    }
}

fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route) {

        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}