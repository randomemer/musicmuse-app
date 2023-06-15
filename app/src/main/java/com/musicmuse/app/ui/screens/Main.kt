@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.musicmuse.app.ui.components.BottomNav
import com.musicmuse.app.ui.components.TrackPlayer
import com.musicmuse.app.ui.components.TrackPlayerViewModel
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.nav.NavigationHost

@Composable
fun Main() {
  val navController = rememberNavController()
  val trackPlayerViewModel = TrackPlayerViewModel()

  AppTheme {
    Scaffold(
      bottomBar = { BottomNav(navController) },
      content = { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
          NavigationHost(navController, trackPlayerViewModel)

          TrackPlayer(trackPlayerViewModel)
        }
      })
  }
}
