@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.api.SpotifyApiService
import com.musicmuse.app.api.models.SpotifyCategoriesResponse
import com.musicmuse.app.api.models.SpotifyCategory
import com.musicmuse.app.ui.nav.ExploreNavGraph
import com.musicmuse.app.utils.GlobalData
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ExploreViewModel : ViewModel() {
  private var _categories: List<SpotifyCategory>? by mutableStateOf(null)
  var errorMessage by mutableStateOf("")

  val categories get() = _categories


  fun getCategories() {
    viewModelScope.launch {
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
  LaunchedEffect(Unit) {
    viewModel.getCategories()
  }

  val categories = viewModel.categories

  if (viewModel.errorMessage.isEmpty() && categories != null) {
    Column(Modifier.fillMaxSize()) {
      Column(
        Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          "Explore",
          style = MaterialTheme.typography.h4,
          fontWeight = FontWeight.Bold
        )

        val interactionSource = remember { MutableInteractionSource() }
        TextField(
          modifier = Modifier.fillMaxWidth().clickable(
            indication = null,
            interactionSource = interactionSource
          ) {
            navController.navigate(ExploreNavGraph.search.route)
          },
          enabled = false,
          readOnly = true,
          value = TextFieldValue(""),
          placeholder = { Text("Search") },
          shape = RoundedCornerShape(10),
          onValueChange = { },
          colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
          ),
          leadingIcon = {
            Icon(Icons.Rounded.Search, "Search")
          }
        )
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
        items(categories) {
          Card(
            elevation = 3.dp,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.clickable(true, onClick = {
              GlobalData.put(it.id, it)
              navController.navigate("explore_category/${it.id}")
            })
          ) {
            AsyncImage(
              model = it.icons[0].href,
              contentDescription = null,
              contentScale = ContentScale.Crop
            )
          }
        }
      }
    }
  } else {
    Text(viewModel.errorMessage)
  }
}
