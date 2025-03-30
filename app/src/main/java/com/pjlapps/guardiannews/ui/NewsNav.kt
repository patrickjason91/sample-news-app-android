package com.pjlapps.guardiannews.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pjlapps.guardiannews.MainViewModel
import kotlinx.serialization.Serializable

/**
 * Serializable object to represent a news list in the NewsNav UI
 */
@Serializable
data object NewsListPage

/**
 * Serializable object to represent news detail in the NewsNav UI
 */
@Serializable
data class NewsDetailsPage(val newsId: String)

@Composable
fun NewsNav(
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NewsListPage,
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding)
    ) {
        composable<NewsListPage> {
            NewsListScreen(
                navController,
                viewModel
            )
        }
        composable<NewsDetailsPage> {
            val newsDetailsPage = it.toRoute<NewsDetailsPage>()
            NewsDetailScreen(
                navController,
                viewModel,
                newsDetailsPage.newsId
            )
        }
    }
}