package com.anonlatte.natgeo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anonlatte.natgeo.data.navigation.NavDestinations
import com.anonlatte.natgeo.ui.article.ARGS_ARTICLE_ID
import com.anonlatte.natgeo.ui.article.ArticleScreen
import com.anonlatte.natgeo.ui.home.Home
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModelImpl

@Composable
fun NatGeoApp() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController = navController, startDestination = NavDestinations.HOME) {
            composable(NavDestinations.HOME) {
                Home(viewModel = hiltViewModel<HomeViewModelImpl>(), navController = navController)
            }
            composable(
                route = "${NavDestinations.ARTICLE}/{$ARGS_ARTICLE_ID}",
                arguments = listOf(navArgument(ARGS_ARTICLE_ID) { type = NavType.IntType })
            ) {
                ArticleScreen(
                    viewModel = hiltViewModel(),
                    navController = navController,
                    articleId = it.arguments!!.getInt(ARGS_ARTICLE_ID)
                )
            }
        }
    }
}