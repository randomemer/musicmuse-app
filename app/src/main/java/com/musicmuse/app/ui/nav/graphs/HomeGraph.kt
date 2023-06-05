package com.musicmuse.app.ui.nav.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.musicmuse.app.ui.screens.Home

fun NavGraphBuilder.homeGraph(navController: NavController) {
  navigation(route = "home", startDestination = "home_main") {
    composable(route = "home_main") {
      Home()
    }
  }
}
