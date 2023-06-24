@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.api.models.SpotifyTrack
import com.musicmuse.app.models.PlaylistViewModel
import com.musicmuse.app.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Playlist(
  playlistViewModel: PlaylistViewModel,
  navController: NavController
) {
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
      Scaffold(topBar = {
        TopAppBar(
          navigationIcon = { NavBackButton(navController) },
          title = {
            Text(
              playlist.name,
              style = MaterialTheme.typography.titleLarge,
            )
          })
      }) { padding ->
        LazyColumn(
          modifier = Modifier.fillMaxSize().padding(padding),
          verticalArrangement = Arrangement.spacedBy(9.dp),
          contentPadding = PaddingValues(
            start = 24.dp,
            top = 24.dp,
            end = 24.dp,
            bottom = 24.dp + TrackPlayerHeight
          )
        ) {
          item {
            Box(Modifier.padding(bottom = 24.dp).fillMaxWidth()) {
              AsyncImage(
                model = playlist.images.first().href,
                contentDescription = playlist.name,
                modifier = Modifier.size(256.dp).align(Alignment.Center)
              )
            }
          }

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
