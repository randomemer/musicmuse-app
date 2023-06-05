@file:Suppress("FunctionName")

package com.musicmuse.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musicmuse.app.ui.nav.graphs.exploreGraph
import com.musicmuse.app.ui.nav.graphs.homeGraph
import com.musicmuse.app.ui.screens.Account


@Composable
fun NavigationHost(navController: NavHostController) {
  NavHost(navController, startDestination = NavigationItem.Home.route) {
    homeGraph(navController)

    exploreGraph(navController)

    composable(NavigationItem.Profile.route) {
      Account()
    }
  }
}
