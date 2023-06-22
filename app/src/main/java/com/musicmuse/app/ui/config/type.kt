package com.musicmuse.app.ui.config

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.musicmuse.app.R

val Lexend = FontFamily(
  Font(R.font.lexend)
)

val typography = Typography()

val MusicMuseTypography = Typography(
  displayLarge = typography.displayLarge.copy(fontFamily = Lexend),
  displayMedium = typography.displayMedium.copy(fontFamily = Lexend),
  displaySmall = typography.displaySmall.copy(fontFamily = Lexend),
  headlineLarge = typography.headlineLarge.copy(fontFamily = Lexend),
  headlineMedium = typography.headlineMedium.copy(fontFamily = Lexend),
  headlineSmall = typography.headlineSmall.copy(fontFamily = Lexend),
  titleLarge = typography.titleLarge.copy(fontFamily = Lexend),
  titleMedium = typography.titleMedium.copy(fontFamily = Lexend),
  titleSmall = typography.titleSmall.copy(fontFamily = Lexend),
  bodyLarge = typography.bodyLarge.copy(fontFamily = Lexend),
  bodyMedium = typography.bodyMedium.copy(fontFamily = Lexend),
  bodySmall = typography.bodySmall.copy(fontFamily = Lexend),
  labelLarge = typography.labelLarge.copy(fontFamily = Lexend),
  labelMedium = typography.labelMedium.copy(fontFamily = Lexend),
  labelSmall = typography.labelSmall.copy(fontFamily = Lexend)
)
