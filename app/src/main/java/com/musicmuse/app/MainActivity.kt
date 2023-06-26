package com.musicmuse.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.screens.Main
import com.musicmuse.app.utils.GlobalData

val LocalActivity = staticCompositionLocalOf<MainActivity> {
  error("LocalActivity is not present")
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    supportActionBar?.hide()

    FirebaseApp.initializeApp(this)

    GlobalData.put("client_id", getString(R.string.spotify_client_id))
    GlobalData.put("client_secret", getString(R.string.spotify_client_secret))

    setContent {
      CompositionLocalProvider(LocalActivity provides this@MainActivity) {
        AppTheme {
          Main()
        }
      }
    }
  }
}
