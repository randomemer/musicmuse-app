@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.models.ExploreViewModel
import com.musicmuse.app.ui.components.ErrorComponent
import com.musicmuse.app.ui.components.Loading
import com.musicmuse.app.ui.components.TrackPlayerHeight
import com.musicmuse.app.ui.nav.ExploreNavGraph
import com.musicmuse.app.utils.GlobalData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Explore(viewModel: ExploreViewModel, navController: NavController) {
  LaunchedEffect(Unit) {
    viewModel.getCategories()
  }

  val categories = viewModel.categories

  Column(Modifier.fillMaxSize()) {
    Column(
      Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        "Explore",
        style = MaterialTheme.typography.headlineMedium,
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


    if (viewModel.errorMessage.isEmpty()) {
      if (categories == null) Loading()
      else {
        LazyVerticalGrid(
          columns = GridCells.Adaptive(minSize = 156.dp),
          verticalArrangement = Arrangement.spacedBy(18.dp),
          horizontalArrangement = Arrangement.spacedBy(18.dp),
          contentPadding = PaddingValues(
            start = 24.dp,
            top = 12.dp,
            end = 24.dp,
            bottom = 24.dp + TrackPlayerHeight
          )
        ) {
          items(categories) {
            Card(
              shape = RoundedCornerShape(5.dp),
              modifier = Modifier.clickable(true, onClick = {
                GlobalData.put(it.id, it)
                navController.navigate("explore_category/${it.id}")
              })
            ) {
              AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = it.icons[0].href,
                contentDescription = null,
                contentScale = ContentScale.Crop
              )
            }
          }
        }
      }
    } else {
      ErrorComponent(viewModel.errorMessage)
    }
  }
}
