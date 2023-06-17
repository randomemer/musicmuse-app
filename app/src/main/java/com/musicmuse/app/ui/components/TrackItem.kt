@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.api.models.SpotifyTrack

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackItem(track: SpotifyTrack) {
  val trackPlayerVM: TrackPlayerViewModel = viewModel(LocalActivity.current)
  val artists = track.artists.map { item -> item.name }
  val artistsString = artists.joinToString(", ")

  Card(
    elevation = 3.dp, shape = RoundedCornerShape(5.dp),
    modifier = Modifier.fillMaxWidth().height(56.dp)
      .clickable(true, onClick = {
        trackPlayerVM.playTrack(track)
      })
  ) {


    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      AsyncImage(
        modifier = Modifier.fillMaxHeight(),
        model = track.album.images.last().href,
        contentDescription = null
      )
      Column(Modifier.align(Alignment.CenterVertically)) {
        Text(
          track.name,
          maxLines = 1,
          modifier = Modifier.basicMarquee(),
          style = MaterialTheme.typography.body1,
          fontWeight = FontWeight.Bold,
        )

        Text(
          artistsString,
          maxLines = 1,
          modifier = Modifier.basicMarquee(),
          style = MaterialTheme.typography.body2
        )
      }
    }
  }
}
