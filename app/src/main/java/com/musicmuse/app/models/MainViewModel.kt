package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {
  private var _currentUser by mutableStateOf(Firebase.auth.currentUser)
  val currentUser get() = _currentUser

  init {
    Firebase.auth.addAuthStateListener { auth ->
      _currentUser = auth.currentUser
    }
  }
}
