@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.models.CategoryViewModel
import com.musicmuse.app.ui.components.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Category(viewModel: CategoryViewModel, navController: NavController) {
  LaunchedEffect(Unit) {
    viewModel.getCategoryPlaylists()
  }

  val playlists = viewModel.playlists?.items
  val category = viewModel.category

  Scaffold(
    topBar = {
      TopAppBar(
        navigationIcon = { NavBackButton(navController) },
        title = {
          Text(
            category.name,
            style = MaterialTheme.typography.titleLarge,
          )
        })
    }) { paddingValues ->
    Box(Modifier.padding(paddingValues)) {
      if (viewModel.errorMessage.isEmpty()) {
        if (playlists != null) {
          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            contentPadding = PaddingValues(
              start = 24.dp,
              top = 24.dp,
              end = 24.dp,
              bottom = 24.dp + TrackPlayerHeight
            )
          ) {
            item {
              Box(Modifier.padding(bottom = 24.dp).fillMaxWidth()) {
                AsyncImage(
                  model = category.icons.first().href,
                  contentDescription = category.name,
                  modifier = Modifier.size(256.dp).align(Alignment.Center)
                )
              }
            }

            items(playlists) {
              PlaylistItem(it, navController)
            }
          }
        } else {
          Loading()
        }
      } else {
        ErrorComponent(viewModel.errorMessage)
      }
    }
  }
}
