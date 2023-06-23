package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
  val playlists get() = _playlists.value
  val category: SpotifyCategory get() = GlobalData.get(categoryId)

  var errorMessage: String by mutableStateOf("")


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
