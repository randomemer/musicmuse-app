package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifySearchResponse
import com.musicmuse.app.utils.AsyncState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {
  var searchValue by mutableStateOf(TextFieldValue(""))
  var filter by mutableStateOf("tracks")
  private var _status by mutableStateOf(AsyncState.Idle)
  private var _results: SpotifySearchResponse? by mutableStateOf(null)
  private var _errorMessage by mutableStateOf("")

  val results get() = _results
  val status get() = _status
  val errorMessage get() = _errorMessage

  fun search() {
    viewModelScope.launch {
      _status = AsyncState.Pending
      try {
        _results = SpotifyApiService().api.search(searchValue.text)
        _status = AsyncState.Resolved
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        _errorMessage = e.message.toString()
        _status = AsyncState.Rejected
      }
    }
  }
}
