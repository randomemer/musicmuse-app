package com.musicmuse.app.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppNavGraph(
  var route: String,
  var icon: ImageVector,
  var title: String
) {
  object Home : AppNavGraph("home", Icons.Rounded.Home, "Home")
  object Explore : AppNavGraph("explore", Icons.Rounded.Search, "Explore")
  object Profile : AppNavGraph("profile", Icons.Rounded.Person, "Profile")
}

data class NavGraphItem(
  val route: String,
  val arguments: List<NamedNavArgument> = emptyList()
)

object HomeNavGraph {
  val root = NavGraphItem("home_main")
}

object ExploreNavGraph {
  val root = NavGraphItem("explore_main")
  val search = NavGraphItem("explore_search")
  val category = NavGraphItem(
    "explore_category/{categoryId}",
    listOf(navArgument("categoryId") { type = NavType.StringType })
  )
  val playlist = NavGraphItem(
    "explore_playlist/{playlistId}",
    listOf(navArgument("playlistId") { type = NavType.StringType })
  )
}
