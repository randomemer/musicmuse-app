package com.musicmuse.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.ui.screens.Home
import com.musicmuse.app.ui.screens.Login

class MainActivity : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth
  private lateinit var sharedPref: SharedPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    auth = Firebase.auth
    sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)

    val token = sharedPref.getString("spotify_token", "")

    setContent {
      if (token == null || token == "") Login()
      else Home()
    }
  }
}
