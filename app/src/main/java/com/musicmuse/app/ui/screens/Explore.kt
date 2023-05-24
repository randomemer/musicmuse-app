@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.musicmuse.app.utils.SpotifyApiService
import com.musicmuse.app.utils.SpotifyCategory
import com.musicmuse.app.utils.SpotifyPaginatedModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ExploreViewModel : ViewModel() {
  private val _categories = mutableStateOf<SpotifyPaginatedModel<SpotifyCategory>?>(null)
  var errorMessage: String by mutableStateOf("")
  val categories: SpotifyPaginatedModel<SpotifyCategory>? get() = _categories.value

  fun getCategories() {
    viewModelScope.launch {
      try {
        _categories.value = SpotifyApiService().api.getCategories().categories
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
fun Explore(viewModel: ExploreViewModel) {
  var searchValue by remember { mutableStateOf(TextFieldValue("")) }

  LaunchedEffect(Unit, block = {
    viewModel.getCategories()
  })

  val categories = viewModel.categories?.items

  if (viewModel.errorMessage.isEmpty() && categories != null) {
    Column(Modifier.padding(24.dp).fillMaxSize()) {
      Text("Explore", fontSize = 24.sp, fontWeight = FontWeight.Medium)
      TextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchValue,
        placeholder = { Text("Search") },
        shape = RoundedCornerShape(100),
        onValueChange = { searchValue = it }
      )
      LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
        items(categories, itemContent = {
          AsyncImage(model = it.icons[0].href, contentDescription = null)
        })
      }
    }
  } else {
    Text(viewModel.errorMessage)
  }
}
