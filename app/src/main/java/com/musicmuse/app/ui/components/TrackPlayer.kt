@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.musicmuse.app.api.models.SpotifyTrack
import kotlinx.coroutines.flow.MutableStateFlow

class TrackPlayerViewModel : ViewModel() {
  private val _track = MutableStateFlow<SpotifyTrack?>(null)
  val track get() = _track

  private var mediaPlayer: MediaPlayer? = null

  fun playTrack(track: SpotifyTrack) {
    // In case there's no preview url
    if (track.previewUrl == null) {
      println("Can' play this track : ${track.name}")
      return
    }

    _track.value = track
    println(_track.subscriptionCount.value)
    println(_track.value!!.name)
    // If media player is not null, release resources
    mediaPlayer?.release()

    // init the new media player
    val newMediaPLayer = MediaPlayer()
    newMediaPLayer.setDataSource(track.previewUrl)

    val audioAttributes = AudioAttributes.Builder()
    audioAttributes.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
    newMediaPLayer.setAudioAttributes(audioAttributes.build())

    newMediaPLayer.prepare()
    newMediaPLayer.start()

    mediaPlayer = newMediaPLayer
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackPlayer(viewModel: TrackPlayerViewModel) {
  val trackState = viewModel.track.collectAsState()
  val track = trackState.value

  LaunchedEffect(trackState) {
    println("Current Track : ${trackState.value?.name}")
  }

  AnimatedVisibility(
    visible = (track != null),
  ) {
    Card(
      elevation = 3.dp, shape = RoundedCornerShape(5.dp),
      modifier = Modifier.fillMaxWidth().height(64.dp)
    ) {
      track!!
      val artists = track.artists.map { item -> item.name }


      Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
        AsyncImage(
          modifier = Modifier.fillMaxHeight(),
          model = track.album.images.last().href,
          contentDescription = track.name
        )

        Column(Modifier.align(Alignment.CenterVertically)) {
          Text(
            track.name, maxLines = 1,
            modifier = Modifier.basicMarquee(),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
          )

          Text(
            artists.joinToString(separator = ", "),
            maxLines = 1,
            modifier = Modifier.basicMarquee(),
            style = MaterialTheme.typography.body2
          )
        }
      }
    }
  }
}
