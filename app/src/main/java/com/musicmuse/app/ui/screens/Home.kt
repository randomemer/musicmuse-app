package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.musicmuse.app.ui.components.BottomNav
import com.musicmuse.app.ui.config.AppTheme

@Preview(name = "HomeScreen")
@Composable
fun Home() {
  val navController = rememberNavController()

  AppTheme {
    Scaffold(bottomBar = { BottomNav(navController) }, content = { paddingValues ->
      Box(Modifier.padding(paddingValues)) {
        Text("Sup brah")
      }
    })
  }
}
