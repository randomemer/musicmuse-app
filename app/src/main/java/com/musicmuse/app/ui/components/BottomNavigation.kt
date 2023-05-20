@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.musicmuse.app.ui.config.primary

@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(NavigationItem.Home, NavigationItem.History, NavigationItem.Profile)

  BottomNavigation {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    items.forEach { item ->
      BottomNavigationItem(
        icon = { Icon(item.icon, item.title) },
        alwaysShowLabel = false,
        selectedContentColor = primary,
        unselectedContentColor = Color.White.copy(0.4f),
        selected = currentRoute == item.route,
        onClick = {
          navController.navigate(item.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.startDestinationRoute?.let { route ->
              popUpTo(route) {
                saveState = true
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
