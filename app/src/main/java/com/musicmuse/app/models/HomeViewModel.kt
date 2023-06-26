package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyFeaturedPlaylistsResponse
import com.musicmuse.app.utils.AsyncState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {
  private var _status by mutableStateOf(AsyncState.Idle)
  private var _results: SpotifyFeaturedPlaylistsResponse? by mutableStateOf(null)
  private var _errorMessage by mutableStateOf("")

  val status get() = _status
  val results get() = _results
  val errorMessage get() = _errorMessage

  suspend fun getFeaturedPlaylists() {
    viewModelScope.launch {
      _status = AsyncState.Pending
      try {
        _results = SpotifyApiService().api.getFeaturedPlaylists()
        _status = AsyncState.Resolved
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        _errorMessage = e.message.toString()
        _status = AsyncState.Rejected
      }
    }
  }
}
