package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.api.SpotifyAuthApiService
import com.musicmuse.app.utils.AsyncState
import com.musicmuse.app.utils.GlobalData
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {
  private var _tokenStatus by mutableStateOf(AsyncState.Idle)
  val tokenStatus get() = _tokenStatus

  private var _tokenErrorMessage = ""
  val tokenErrorMessage get() = _tokenErrorMessage


  private var _currentUser by mutableStateOf(Firebase.auth.currentUser)
  val currentUser get() = _currentUser

  init {
    Firebase.auth.addAuthStateListener { auth ->
      _currentUser = auth.currentUser
    }
  }

  fun getSpotifyToken(clientId: String, clientSecret: String) {
    viewModelScope.launch {
      _tokenStatus = AsyncState.Pending
      try {
        val resp = SpotifyAuthApiService().getAuthToken()
        GlobalData.put("spotify_token", resp.accessToken)
        _tokenStatus = AsyncState.Resolved
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        _tokenErrorMessage = e.message.toString()
        _tokenStatus = AsyncState.Rejected
      }
    }
  }
}
