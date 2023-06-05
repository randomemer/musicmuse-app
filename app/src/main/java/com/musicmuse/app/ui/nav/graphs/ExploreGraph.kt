package com.musicmuse.app.ui.nav.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.musicmuse.app.ui.screens.Category
import com.musicmuse.app.ui.screens.CategoryViewModel
import com.musicmuse.app.ui.screens.Explore
import com.musicmuse.app.ui.screens.ExploreViewModel

fun NavGraphBuilder.exploreGraph(navController: NavController) {
  navigation(route = "explore", startDestination = "explore_main") {
    val exploreViewModel = ExploreViewModel()
    val categoryViewModel = CategoryViewModel()

    composable(route = "explore_main") {
      Explore(exploreViewModel)
    }

    composable(route = "explore_category") {
      Category(categoryViewModel)
    }
  }
}
