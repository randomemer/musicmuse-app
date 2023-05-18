package com.musicmuse.app.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.musicmuse.app.ui.config.AppTheme

@Composable
fun BottomNav(navController: NavController) {
  val items = listOf(NavigationItem.Home, NavigationItem.History, NavigationItem.Profile)

  AppTheme {
    BottomNavigation {
      items.forEach { item ->
        BottomNavigationItem(
          icon = { Icon(item.icon, item.title) },
          label = { Text(item.title) },
          alwaysShowLabel = false,
          selected = false,
          onClick = {},
        )
      }
    }
  }
}
