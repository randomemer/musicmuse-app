package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyPlaylistResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PlaylistViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val playlistId: String = checkNotNull(savedStateHandle["playlistId"])

  private val _playlist = mutableStateOf<SpotifyPlaylistResponse?>(null)
  val playlist get() = _playlist.value

  var errorMessage: String by mutableStateOf("")

  fun getPlaylist() {
    viewModelScope.launch {
      try {
        val resp = SpotifyApiService().api.getPlaylist(playlistId)
        _playlist.value = resp
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        errorMessage = e.message.toString()
      }
    }
  }
}
