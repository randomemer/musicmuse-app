@file:Suppress("FunctionName")

package com.musicmuse.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musicmuse.app.ui.components.TrackPlayerViewModel
import com.musicmuse.app.ui.nav.graphs.ExploreGraph
import com.musicmuse.app.ui.nav.graphs.HomeGraph
import com.musicmuse.app.ui.screens.Account


@Composable
fun NavigationHost(
  navController: NavHostController,
  trackPlayerVM: TrackPlayerViewModel
) {
  NavHost(navController, startDestination = AppNavGraph.Home.route) {
    HomeGraph(navController)

    ExploreGraph(navController, trackPlayerVM)

    composable(AppNavGraph.Profile.route) {
      Account()
    }
  }
}
