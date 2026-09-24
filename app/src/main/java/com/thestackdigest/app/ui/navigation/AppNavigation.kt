package com.thestackdigest.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thestackdigest.app.ui.feed.FeedRoute
import com.thestackdigest.app.ui.saved.SavedRoute
import com.thestackdigest.app.ui.saved.SavedScreen
import com.thestackdigest.app.ui.settings.SettingsScreen

object Routes {
    const val FEED = "feed"
    const val SAVED = "saved"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Routes.FEED
    ) {

        composable(Routes.FEED) {
            FeedRoute(
                paddingValues = paddingValues
            )
        }

        composable(Routes.SAVED) {
            SavedRoute(
                paddingValues = paddingValues
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                paddingValues = paddingValues
            )
        }
    }
}