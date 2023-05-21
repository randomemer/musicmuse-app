package com.musicmuse.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
  object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
  object History : NavigationItem("history", Icons.Rounded.Search, "History")
  object Profile : NavigationItem("profile", Icons.Rounded.Person, "Profile")
}
