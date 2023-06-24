package com.musicmuse.app

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.api.SpotifyAuthApiService
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.screens.Main
import com.musicmuse.app.utils.GlobalData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

val LocalActivity = staticCompositionLocalOf<MainActivity> {
  error("LocalActivity is not present")
}

class MainActivity : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    supportActionBar?.hide()

    getSpotifyToken()
    FirebaseApp.initializeApp(this)
    auth = Firebase.auth;

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

  @OptIn(DelicateCoroutinesApi::class)
  private fun getSpotifyToken() {
    val content = findViewById<View>(android.R.id.content)

    content.viewTreeObserver.addOnPreDrawListener(object :
      ViewTreeObserver.OnPreDrawListener {
      override fun onPreDraw(): Boolean {
        GlobalScope.launch {
          try {
            val resp = SpotifyAuthApiService().getAuthToken()
            println("spotify token : ${resp.accessToken}")
            GlobalData.put("spotify_token", resp.accessToken)
          } catch (e: HttpException) {
            println("Auth Token Failed : ${e.message()}")
            println(e.response()?.errorBody()?.string())
          }
        }
        content.viewTreeObserver.removeOnPreDrawListener(this)
        return true
      }
    })
  }
}
