package com.musicmuse.app.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
  private val auth = Firebase.auth

  fun signInWithCredential(credential: AuthCredential) =
    viewModelScope.launch {
      try {
        auth.signInWithCredential(credential).await()
      } catch (e: Exception) {
        println("Error during sign in credential")
        e.printStackTrace()
      }
    }
}
