package com.musicmuse.app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyCategoriesResponse
import com.musicmuse.app.api.models.SpotifyCategory
import com.musicmuse.app.utils.AsyncState
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ExploreViewModel : ViewModel() {
  private var _categories: List<SpotifyCategory>? by mutableStateOf(null)
  var status by mutableStateOf(AsyncState.Idle)
  var errorMessage by mutableStateOf("")

  val categories get() = _categories


  fun getCategories() {
    viewModelScope.launch {
      status = AsyncState.Pending
      try {
        var resp: SpotifyCategoriesResponse
        val items = mutableListOf<SpotifyCategory>()
        var i = 0

        do {
          resp = SpotifyApiService().api.getCategories(offset = i * 50)
          items.addAll(resp.categories.items)
          i++
        } while (resp.categories.next != null)

        _categories = items
        status = AsyncState.Resolved
      } catch (e: Exception) {
        e.printStackTrace()
        if (e is HttpException) {
          println(e.response()?.errorBody()?.string())
        }
        errorMessage = e.message.toString()
        status = AsyncState.Rejected
      }
    }
  }
}
