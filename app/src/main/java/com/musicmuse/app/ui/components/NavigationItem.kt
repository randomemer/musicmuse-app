package com.musicmuse.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
  object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
  object History : NavigationItem("history", Icons.Rounded.Notifications, "History")
  object Profile : NavigationItem("Profile", Icons.Rounded.Person, "Profile")
}
