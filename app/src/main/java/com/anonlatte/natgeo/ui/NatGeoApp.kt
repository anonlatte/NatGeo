package com.anonlatte.natgeo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anonlatte.natgeo.ui.home.Home
import com.anonlatte.natgeo.ui.navigation.NavDestinations

@Composable
fun NatGeoApp() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController = navController, startDestination = NavDestinations.HOME) {
            composable(NavDestinations.HOME) {
                Home(viewModel = hiltViewModel())
            }
        }
    }
}