@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifySearchResponse
import com.musicmuse.app.ui.components.Loading
import com.musicmuse.app.ui.components.PlaylistItem
import com.musicmuse.app.ui.components.SearchField
import com.musicmuse.app.ui.components.TrackItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {
  var searchValue by mutableStateOf(TextFieldValue(""))
  var filter by mutableStateOf("tracks")
  private var _isSearching by mutableStateOf(false)
  private var _results: SpotifySearchResponse? by mutableStateOf(null)
  private var _errorMessage by mutableStateOf("")

  val results get() = _results
  val isSearching get() = _isSearching
  val errorMessage get() = _errorMessage

  fun search() {
    viewModelScope.launch {
      _isSearching = true
      try {
        _results = SpotifyApiService().api.search(searchValue.text)
        println(_results!!.tracks.items.size)
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        _errorMessage = e.message.toString()
      }
      _isSearching = false
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Search(viewModel: SearchViewModel, navController: NavController) {
  Scaffold(topBar = {
    TopAppBar {
      IconButton(onClick = {
        navController.popBackStack()
      }) {
        Icon(Icons.Rounded.ArrowBack, "Go Back")
      }

      SearchField(viewModel)
    }
  }) { paddingValues ->
    Box(Modifier.padding(paddingValues)) {
      if (viewModel.isSearching) Loading()
      else if (viewModel.errorMessage.isNotEmpty()) {
        Text(viewModel.errorMessage)
      } else if (viewModel.results != null) {
        Column(Modifier.fillMaxSize()) {
          // Chips
          Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
          ) {
            FilterChip(
              selected = (viewModel.filter == "tracks"),
              onClick = { viewModel.filter = "tracks" }) {
              Text("Tracks")
            }

            FilterChip(
              selected = (viewModel.filter == "playlists"),
              onClick = { viewModel.filter = "playlists" }) {
              Text("Playlists")
            }
          }

          // Search Results
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            contentPadding = PaddingValues(
              start = 24.dp,
              top = 12.dp,
              end = 24.dp,
              bottom = 36.dp
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
      } else {
        Text("Start searching!")
      }
    }
  }
}
