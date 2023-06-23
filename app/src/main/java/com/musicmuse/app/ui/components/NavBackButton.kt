@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NavBackButton(navController: NavController) {
  IconButton(onClick = {
    navController.popBackStack()
  }) {
    Icon(Icons.Rounded.ArrowBack, "Go Back")
  }
}
