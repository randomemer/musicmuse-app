package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifySearchResponse
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
