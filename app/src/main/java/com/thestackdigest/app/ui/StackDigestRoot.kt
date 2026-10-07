package com.thestackdigest.app.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.thestackdigest.app.ui.navigation.AppNavigation
import com.thestackdigest.app.ui.navigation.BottomNavigationBar
import com.thestackdigest.app.ui.navigation.Routes
import com.thestackdigest.app.ui.permission.NotificationPermissionHandler
import com.thestackdigest.app.ui.settings.AppTheme
import com.thestackdigest.app.ui.theme.TheStackDigestTheme

@Composable
fun StackDigestRoot(
    notificationArticleId: String?,
    onNotificationArticleHandled: () -> Unit,
    viewModel: AppViewModel = hiltViewModel()
) {
    val appTheme by
    viewModel.appTheme.collectAsStateWithLifecycle()

    val useDarkTheme =
        when (appTheme) {
            AppTheme.SYSTEM -> isSystemInDarkTheme()
            AppTheme.LIGHT -> false
            AppTheme.DARK -> true
        }

    TheStackDigestTheme(
        darkTheme = useDarkTheme
    ) {

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