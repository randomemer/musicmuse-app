@file:Suppress("FunctionName")

package com.musicmuse.app.ui.config

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

val DarkColourPalette =
  darkColors(primary = primary, secondary = secondary, background = background, surface = surface, onPrimary = text)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  MaterialTheme(colors = DarkColourPalette, content = content)
}
