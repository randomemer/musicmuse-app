@file:Suppress("FunctionName")

package com.musicmuse.app.ui.nav.graphs

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.musicmuse.app.models.CategoryViewModel
import com.musicmuse.app.models.ExploreViewModel
import com.musicmuse.app.models.PlaylistViewModel
import com.musicmuse.app.models.SearchViewModel
import com.musicmuse.app.ui.nav.AppNavGraph
import com.musicmuse.app.ui.nav.ExploreNavGraph
import com.musicmuse.app.ui.screens.Category
import com.musicmuse.app.ui.screens.Explore
import com.musicmuse.app.ui.screens.Playlist
import com.musicmuse.app.ui.screens.Search


fun NavGraphBuilder.ExploreGraph(navController: NavController) {
  navigation(
    route = AppNavGraph.Explore.route,
    startDestination = ExploreNavGraph.root.route
  ) {
    val exploreViewModel = ExploreViewModel()

    composable(route = ExploreNavGraph.root.route) {
      Explore(exploreViewModel, navController)
    }

    composable(route = ExploreNavGraph.search.route) {
      val parentEntry = remember(it) {
        navController.getBackStackEntry(ExploreNavGraph.search.route)
      }
      val searchViewModel: SearchViewModel = viewModel(parentEntry)

      Search(searchViewModel, navController)
    }

    composable(
      route = ExploreNavGraph.category.route,
      arguments = ExploreNavGraph.category.arguments
    ) {
      // Retrieve the NavBackStackEntry of this route
      val parentEntry = remember(it) {
        navController.getBackStackEntry(ExploreNavGraph.category.route)
      }
      // Get the ViewModel scoped to the `parentNavigationRoute` Nav graph
      val categoryViewModel: CategoryViewModel = viewModel(parentEntry)

      Category(categoryViewModel, navController)
    }

    composable(
      route = ExploreNavGraph.playlist.route,
      arguments = ExploreNavGraph.playlist.arguments
    ) {
      val parentEntry = remember(it) {
        navController.getBackStackEntry(ExploreNavGraph.playlist.route)
      }
      val playlistViewModel: PlaylistViewModel = viewModel(parentEntry)

      Playlist(playlistViewModel)
    }
  }
}
