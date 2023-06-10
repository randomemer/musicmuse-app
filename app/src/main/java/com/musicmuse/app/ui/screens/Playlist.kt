@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.*
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

@Composable
fun Playlist(playlistViewModel: PlaylistViewModel) {
  LaunchedEffect(Unit) {
    playlistViewModel.getPlaylist()
  }

  val playlist = playlistViewModel.playlist
  val tracks = playlist?.tracks
  val errorMessage = playlistViewModel.errorMessage

  if (errorMessage.isEmpty()) {
    // when loading
    if (playlist == null) {
      Text("Loading")
    }
    // when loaded
    else {
      println(tracks?.items?.size)
    }
  }
  // when error
  else {
    Text(errorMessage)
  }
}
