@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.api.models.SpotifyTrack
import com.musicmuse.app.models.PlaylistViewModel
import com.musicmuse.app.ui.components.*

@Composable
fun Playlist(playlistViewModel: PlaylistViewModel) {
  val trackPlayerVM: TrackPlayerViewModel = viewModel(LocalActivity.current)

  LaunchedEffect(Unit) {
    playlistViewModel.getPlaylist()
  }

  val playlist = playlistViewModel.playlist
  val tracks = playlist?.tracks

  if (playlistViewModel.errorMessage.isEmpty()) {
    // when loading
    if (playlist == null) Loading()
    // when loaded
    else {
      Column(Modifier.fillMaxSize()) {
        Box(Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp)) {
          Text(
            playlist.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
          )
        }

        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(9.dp),
          contentPadding = PaddingValues(
            start = 24.dp,
            top = 12.dp,
            end = 24.dp,
            bottom = 24.dp + TrackPlayerHeight
          )
        ) {
          items(tracks!!.items) {
            Card(
              shape = RoundedCornerShape(5.dp),
              modifier = Modifier.fillMaxWidth().height(56.dp)
                .clickable(true, onClick = {
                  println("clicked ${it.track.uri}")
                  trackPlayerVM.playTrack(it.track as SpotifyTrack)
                })
            ) {
              when (it.track) {
                is SpotifyTrack -> {
                  TrackItem(it.track)
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
    ErrorComponent(playlistViewModel.errorMessage)
  }
}
