@file:Suppress("FunctionName")

package com.musicmuse.app.ui.nav.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.musicmuse.app.ui.nav.AppNavGraph
import com.musicmuse.app.ui.nav.HomeNavGraph
import com.musicmuse.app.ui.screens.Home

fun NavGraphBuilder.HomeGraph(navController: NavController) {
  navigation(
    route = AppNavGraph.Home.route,
    startDestination = HomeNavGraph.root.route
  ) {
    composable(route = HomeNavGraph.root.route) {
      Home()
    }
  }
}
