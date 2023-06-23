@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.musicmuse.app.models.CategoryViewModel
import com.musicmuse.app.ui.components.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Category(viewModel: CategoryViewModel, navController: NavController) {
  LaunchedEffect(Unit) {
    viewModel.getCategoryPlaylists()
  }

  val playlists = viewModel.playlists?.items
  val appBarImagePainter = rememberAsyncImagePainter(
    viewModel.category.icons.first().href,
    contentScale = ContentScale.FillBounds
  )
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
    rememberTopAppBarState()
  )


  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      LargeTopAppBar(
        modifier = Modifier.paint(appBarImagePainter),
        scrollBehavior = scrollBehavior,
        navigationIcon = { NavBackButton(navController) },
        title = {
          Text(
            viewModel.category.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
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
