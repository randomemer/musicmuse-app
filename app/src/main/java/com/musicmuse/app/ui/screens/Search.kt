@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.musicmuse.app.models.SearchViewModel
import com.musicmuse.app.ui.components.*
import com.musicmuse.app.ui.config.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: SearchViewModel, navController: NavController) {
  Scaffold(topBar = {
    TopAppBar(
      title = { SearchField(viewModel) },
      navigationIcon = { NavBackButton(navController) })
  }) { paddingValues ->
    Box(Modifier.padding(paddingValues)) {
      if (viewModel.isSearching) Loading()
      else if (viewModel.errorMessage.isNotEmpty()) ErrorComponent(viewModel.errorMessage)
      else if (viewModel.results != null) {
        Column(Modifier.fillMaxSize()) {
          // Chips
          Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
          ) {
            FilterChip(
              label = { Text("Tracks") },
              selected = (viewModel.filter == "tracks"),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = primary,
                selectedLabelColor = Color.Black
              ),
              onClick = { viewModel.filter = "tracks" }
            )

            FilterChip(
              label = { Text("Playlists") },
              selected = (viewModel.filter == "playlists"),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = primary,
                selectedLabelColor = Color.Black
              ),
              onClick = { viewModel.filter = "playlists" }
            )
          }

          // Search Results
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            contentPadding = PaddingValues(
              start = 24.dp,
              top = 12.dp,
              end = 24.dp,
              bottom = 24.dp + TrackPlayerHeight
            )
          ) {
            if (viewModel.filter == "tracks") {
              items(viewModel.results!!.tracks.items) { track -> TrackItem(track) }
            }
            if (viewModel.filter == "playlists") {
              items(viewModel.results!!.playlists.items) { playlist ->
                PlaylistItem(
                  playlist, navController
                )
              }
            }
          }
        }
      } else SearchIdle()
    }
  }
}
