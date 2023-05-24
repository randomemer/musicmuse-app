@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musicmuse.app.ui.screens.Account
import com.musicmuse.app.ui.screens.Explore
import com.musicmuse.app.ui.screens.ExploreViewModel
import com.musicmuse.app.ui.screens.Home


@Composable
fun NavigationHost(navController: NavHostController) {
  val exploreViewModel = ExploreViewModel()

  NavHost(navController, startDestination = NavigationItem.Home.route) {
    composable(NavigationItem.Home.route) {
      Home()
    }

    composable(NavigationItem.History.route) {
      Explore(exploreViewModel)
    }

    composable(NavigationItem.Profile.route) {
      Account()
    }
  }
}
