@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.models.HomeViewModel
import com.musicmuse.app.models.MainViewModel
import com.musicmuse.app.ui.components.ErrorComponent
import com.musicmuse.app.ui.components.Loading
import com.musicmuse.app.ui.components.TrackPlayerHeight
import com.musicmuse.app.utils.AsyncState
import com.musicmuse.app.utils.GlobalData
import com.musicmuse.app.utils.welcomeMessage
import java.util.*

@Composable
fun Home(viewModel: HomeViewModel, navController: NavController) {
  LaunchedEffect(Unit) {
    viewModel.getFeaturedPlaylists()
  }

  val rightNow = Calendar.getInstance()
  val mainViewModel: MainViewModel = viewModel(LocalActivity.current)
  val user = mainViewModel.currentUser

  Column(Modifier.fillMaxSize()) {
    Column(
      Modifier.padding(24.dp, 24.dp, 24.dp, 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        "${welcomeMessage(rightNow.get(Calendar.HOUR_OF_DAY))}, ${user?.displayName}",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
      )
    }

    when (viewModel.status) {
      AsyncState.Pending -> Loading()

      AsyncState.Rejected -> ErrorComponent(viewModel.errorMessage)

      AsyncState.Resolved -> {
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
          items(viewModel.results!!.playlists.items) {
            Card(
              shape = RoundedCornerShape(5.dp),
              modifier = Modifier.size(156.dp).clickable(true, onClick = {
                GlobalData.put(it.id, it)
                navController.navigate("home_playlist/${it.id}")
              })
            ) {
              AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = it.images[0].href,
                contentDescription = null,
                contentScale = ContentScale.Crop
              )
            }
          }
        }
      }

      AsyncState.Idle -> {}
    }
  }
}
