@file:Suppress("FunctionName")

package com.musicmuse.app.ui.nav.graphs

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.musicmuse.app.models.HomeViewModel
import com.musicmuse.app.models.PlaylistViewModel
import com.musicmuse.app.ui.nav.AppNavGraph
import com.musicmuse.app.ui.nav.HomeNavGraph
import com.musicmuse.app.ui.screens.Home
import com.musicmuse.app.ui.screens.Playlist

fun NavGraphBuilder.HomeGraph(navController: NavController) {
  navigation(
    route = AppNavGraph.Home.route,
    startDestination = HomeNavGraph.root.route
  ) {
    composable(route = HomeNavGraph.root.route) {
      val parentEntry = remember(it) {
        navController.getBackStackEntry(HomeNavGraph.root.route)
      }
      val homeViewModel: HomeViewModel = viewModel(parentEntry)

      Home(homeViewModel, navController)
    }

    composable(
      route = HomeNavGraph.playlist.route,
      arguments = HomeNavGraph.playlist.arguments
    ) {
      val parentEntry = remember(it) {
        navController.getBackStackEntry(HomeNavGraph.playlist.route)
      }
      val playlistViewModel: PlaylistViewModel = viewModel(parentEntry)

      Playlist(playlistViewModel, navController)
    }
  }
}
