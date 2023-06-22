@file:Suppress("FunctionName")

package com.musicmuse.app.ui.config

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val DarkColourPalette =
  darkColorScheme(
    primary = primary,
    secondary = secondary,
    background = background,
    surface = surface,
    onPrimary = text,
  )

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  val elevationOverlay = LocalElevationOverlay.current
  val bottomNavColor =
    elevationOverlay?.apply(surface, BottomNavigationDefaults.Elevation)
  val topBarColor =
    elevationOverlay?.apply(surface, AppBarDefaults.TopAppBarElevation)

  val systemUiController = rememberSystemUiController()

  SideEffect {
    if (bottomNavColor != null) systemUiController.setNavigationBarColor(
      bottomNavColor
    )
    if (topBarColor != null) systemUiController.setStatusBarColor(topBarColor)
  }

  MaterialTheme(
    content = content,
    colorScheme = DarkColourPalette,
    typography = MusicMuseTypography
  )
}
