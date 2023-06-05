package com.musicmuse.app.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
  object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
  object Explore : NavigationItem("explore", Icons.Rounded.Search, "Explore")
  object Profile : NavigationItem("profile", Icons.Rounded.Person, "Profile")
}
