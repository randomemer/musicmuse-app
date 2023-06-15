@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyPlaylistResponse
import com.musicmuse.app.api.models.SpotifyTrack
import com.musicmuse.app.ui.components.Loading
import com.musicmuse.app.ui.components.TrackPlayerViewModel
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Playlist(
  playlistViewModel: PlaylistViewModel,
  trackPlayerVM: TrackPlayerViewModel
) {
  val context = LocalContext.current
  LaunchedEffect(Unit) {
    playlistViewModel.getPlaylist()
  }

  val playlist = playlistViewModel.playlist
  val tracks = playlist?.tracks
  val errorMessage = playlistViewModel.errorMessage

  if (errorMessage.isEmpty()) {
    // when loading
    if (playlist == null) Loading()
    // when loaded
    else {
      Column(Modifier.fillMaxSize()) {
        Box(Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp)) {
          Text(
            playlist.name,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
          )
        }

        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(9.dp),
          contentPadding = PaddingValues(
            start = 24.dp,
            top = 12.dp,
            end = 24.dp,
            bottom = 36.dp
          )
        ) {
          items(tracks!!.items) {
            Card(
              elevation = 3.dp, shape = RoundedCornerShape(5.dp),
              modifier = Modifier.fillMaxWidth().height(56.dp)
                .clickable(true, onClick = {
                  println("clicked ${it.track.uri}")
                  trackPlayerVM.playTrack(it.track as SpotifyTrack)
                })
            ) {
              when (it.track) {
                is SpotifyTrack -> {
                  val artists = it.track.artists.map { item -> item.name }
                  val artistsString = artists.joinToString(", ")

                  Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(
                      modifier = Modifier.fillMaxHeight(),
                      model = it.track.album.images.last().href,
                      contentDescription = null
                    )
                    Column(Modifier.align(Alignment.CenterVertically)) {
                      Text(
                        it.track.name,
                        maxLines = 1,
                        modifier = Modifier.basicMarquee(),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                      )

                      Text(
                        artistsString,
                        maxLines = 1,
                        modifier = Modifier.basicMarquee(),
                        style = MaterialTheme.typography.body2
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  // when error
  else {
    Text(errorMessage)
  }
}
