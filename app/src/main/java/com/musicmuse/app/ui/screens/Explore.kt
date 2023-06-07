@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.utils.SpotifyApiService
import com.musicmuse.app.utils.SpotifyCategory
import com.musicmuse.app.utils.SpotifyPaginatedModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ExploreViewModel : ViewModel() {
  private val _categories =
    mutableStateOf<SpotifyPaginatedModel<SpotifyCategory>?>(null)
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
fun Explore(viewModel: ExploreViewModel, navController: NavController) {
  var searchValue by remember { mutableStateOf(TextFieldValue("")) }

  LaunchedEffect(Unit, block = {
    viewModel.getCategories()
  })

  val categories = viewModel.categories?.items

  if (viewModel.errorMessage.isEmpty() && categories != null) {
    Column(Modifier.fillMaxSize()) {
      Box {
        Column(
          Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text("Explore", fontSize = 24.sp, fontWeight = FontWeight.Medium)
          TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchValue,
            placeholder = { Text("Search") },
            shape = RoundedCornerShape(100),
            onValueChange = { searchValue = it },
            colors = TextFieldDefaults.textFieldColors(
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              disabledIndicatorColor = Color.Transparent
            )
          )
        }
      }

      LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 156.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(
          start = 24.dp,
          top = 12.dp,
          end = 24.dp,
          bottom = 36.dp
        )
      ) {
        items(categories, itemContent = {
          Card(
            elevation = 3.dp,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.clickable(true, onClick = {
              println("clicked ${it.name}")
              navController.navigate("explore_category/${it.id}")
            })
          ) {
            AsyncImage(
              model = it.icons[0].href,
              contentDescription = null,
              contentScale = ContentScale.Crop
            )
          }
        })
      }
    }
  } else {
    Text(viewModel.errorMessage)
  }
}
