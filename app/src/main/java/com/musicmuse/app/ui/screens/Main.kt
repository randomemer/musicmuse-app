@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.musicmuse.app.ui.components.BottomNav
import com.musicmuse.app.ui.components.TrackPlayer
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.nav.NavigationHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
  val navController = rememberNavController()

  AppTheme {
    Scaffold(
      bottomBar = { BottomNav(navController) },
      content = { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
          NavigationHost(navController)

          Box(Modifier.align(Alignment.BottomCenter)) {
            TrackPlayer()
          }
        }
      }
    )
  }
}
