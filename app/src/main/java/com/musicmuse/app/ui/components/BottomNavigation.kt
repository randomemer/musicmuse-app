@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.musicmuse.app.ui.config.primary
import com.musicmuse.app.ui.nav.AppNavGraph

@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(AppNavGraph.Home, AppNavGraph.Explore, AppNavGraph.Profile)

  NavigationBar {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    items.forEach { item ->
      NavigationBarItem(
        icon = { Icon(item.icon, item.title) },
        alwaysShowLabel = false,
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = primary,
          unselectedIconColor = Color.White.copy(0.4f),
        ),
        selected = currentRoute?.startsWith(item.route) == true,
        onClick = {
          navController.navigate(item.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.startDestinationRoute?.let { route ->
              popUpTo(route) {
                saveState = true
                inclusive = true
              }
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
          }
        },
      )
    }
  }
}
