package com.thestackdigest.app.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thestackdigest.app.ui.detail.ArticleDetailRoute
import com.thestackdigest.app.ui.feed.FeedRoute
import com.thestackdigest.app.ui.saved.SavedRoute
import com.thestackdigest.app.ui.settings.SettingsScreen

object Routes {

    const val FEED = "feed"
    const val SAVED = "saved"
    const val SETTINGS = "settings"

    const val DETAIL = "detail"
    const val ARTICLE_ID = "articleId"

    const val DETAIL_ROUTE =
        "$DETAIL?$ARTICLE_ID={$ARTICLE_ID}"

    fun detailRoute(
        articleId: String
    ): String {

        return "$DETAIL?$ARTICLE_ID=${Uri.encode(articleId)}"
    }
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

        composable(
            route = Routes.FEED
        ) {

            FeedRoute(
                paddingValues = paddingValues,
                onArticleClicked = { article ->

                    navController.navigate(
                        Routes.detailRoute(article.id)
                    )
                }
            )
        }

        composable(
            route = Routes.SAVED
        ) {

            SavedRoute(
                paddingValues = paddingValues,
                onArticleClicked = { article ->

                    navController.navigate(
                        Routes.detailRoute(article.id)
                    )
                }
            )
        }

        composable(
            route = Routes.SETTINGS
        ) {

            SettingsScreen(
                paddingValues = paddingValues
            )
        }

        composable(
            route = Routes.DETAIL_ROUTE,
            arguments = listOf(
                navArgument(Routes.ARTICLE_ID) {
                    type = NavType.StringType
                }
            )
        ) {

            ArticleDetailRoute(
                paddingValues = paddingValues,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}