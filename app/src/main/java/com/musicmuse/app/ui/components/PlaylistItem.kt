@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    shape = RoundedCornerShape(5.dp),
    modifier = Modifier.fillMaxWidth().height(64.dp)
      .clickable(true, onClick = {
        navController.navigate("explore_playlist/${playlist.id}")
      })
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
      AsyncImage(
        modifier = Modifier.fillMaxHeight().width(64.dp),
        model = playlist.images.last().href,
        contentDescription = null
      )
      Column(Modifier.align(Alignment.CenterVertically)) {
        Text(
          playlist.name,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

