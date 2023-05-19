package com.musicmuse.app

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.screens.Home
import com.musicmuse.app.utils.GlobalData
import com.musicmuse.app.utils.SpotifyAuthApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()

    getSpotifyToken()
    auth = Firebase.auth;

    setContent {
      AppTheme {
        Home()
      }
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun getSpotifyToken() {
    val content = findViewById<View>(android.R.id.content)
    val clientId = applicationContext.resources.getString(R.string.spotify_client_id)
    val clientSecret = applicationContext.resources.getString(R.string.spotify_client_secret)

    content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
      override fun onPreDraw(): Boolean {
        GlobalScope.launch {
          try {
            val resp = SpotifyAuthApiService().getAuthToken(clientId, clientSecret)
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
