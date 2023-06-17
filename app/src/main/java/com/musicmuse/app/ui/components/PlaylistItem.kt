@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.musicmuse.app.api.models.SpotifySimplifiedPlaylist

@Composable
fun PlaylistItem(
  playlist: SpotifySimplifiedPlaylist,
  navController: NavController
) {
  Card(
    elevation = 3.dp, shape = RoundedCornerShape(5.dp),
    modifier = Modifier.fillMaxWidth().height(64.dp)
      .clickable(true, onClick = {
        navController.navigate("explore_playlist/${playlist.id}")
      })
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
      AsyncImage(
        modifier = Modifier.fillMaxHeight(),
        model = playlist.images.last().href,
        contentDescription = null
      )
      Column(Modifier.align(Alignment.CenterVertically)) {
        Text(
          playlist.name,
          style = MaterialTheme.typography.body1,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

