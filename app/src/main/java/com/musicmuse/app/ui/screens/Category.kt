@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyCategory
import com.musicmuse.app.api.models.SpotifyPaginatedModel
import com.musicmuse.app.api.models.SpotifySimplifiedPlaylist
import com.musicmuse.app.utils.GlobalData
import kotlinx.coroutines.launch
import retrofit2.HttpException


class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val categoryId: String = checkNotNull(savedStateHandle["categoryId"])

  private val _playlists =
    mutableStateOf<SpotifyPaginatedModel<SpotifySimplifiedPlaylist>?>(null)
  val category get() = GlobalData.get<SpotifyCategory>(categoryId)


  var errorMessage: String by mutableStateOf("")

  val playlists: SpotifyPaginatedModel<SpotifySimplifiedPlaylist>? get() = _playlists.value

  fun getCategoryPlaylists() {
    viewModelScope.launch {
      try {
        val resp = SpotifyApiService().api.getCategoryPlaylists(categoryId)
        _playlists.value = resp.playlists
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
fun Category(viewModel: CategoryViewModel, navController: NavController) {
  LaunchedEffect(Unit) {
    viewModel.getCategoryPlaylists()
  }

  val playlists = viewModel.playlists?.items

  if (viewModel.errorMessage.isEmpty() && playlists != null) {
    Column(Modifier.fillMaxSize()) {
      Box(Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp)) {
        Text(
          viewModel.category.name,
          style = MaterialTheme.typography.h4,
          fontWeight = FontWeight.Bold
        )
      }

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(
          start = 24.dp,
          top = 12.dp,
          end = 24.dp,
          bottom = 36.dp
        )
      ) {
        items(playlists) {
          Card(
            elevation = 3.dp, shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth().height(64.dp)
              .clickable(true, onClick = {
                println("clicked ${it.name}")
                navController.navigate("explore_playlist/${it.id}")
              })
          ) {
            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
              AsyncImage(
                model = it.images.last().href,
                contentDescription = null
              )
              Column(Modifier.align(Alignment.CenterVertically)) {
                Text(
                  it.name,
                  style = MaterialTheme.typography.body1,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }
  } else {
    Text(viewModel.errorMessage)
  }
}