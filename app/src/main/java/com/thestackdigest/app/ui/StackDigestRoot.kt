package com.thestackdigest.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.thestackdigest.app.ui.navigation.AppNavigation
import com.thestackdigest.app.ui.navigation.BottomNavigationBar
import com.thestackdigest.app.ui.navigation.Routes
import com.thestackdigest.app.ui.permission.NotificationPermissionHandler
import com.thestackdigest.app.ui.theme.TheStackDigestTheme

@Composable
fun StackDigestRoot(
    notificationArticleId: String?,
    onNotificationArticleHandled: () -> Unit
) {

    TheStackDigestTheme {

        val navController =
            rememberNavController()

        LaunchedEffect(
            notificationArticleId
        ) {

            val articleId =
                notificationArticleId
                    ?: return@LaunchedEffect

            navController.navigate(
                Routes.detailRoute(
                    articleId
                )
            ) {
                launchSingleTop = true
            }

            onNotificationArticleHandled()
        }

        NotificationPermissionHandler()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
                )
            }
        ) { innerPadding ->

            AppNavigation(
                navController = navController,
                paddingValues = innerPadding
            )
        }
    }
}