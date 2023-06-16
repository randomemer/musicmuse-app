@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.musicmuse.app.api.models.SpotifyTrack
import com.musicmuse.app.ui.config.secondary

class TrackPlayerViewModel : ViewModel() {
  private var _track: SpotifyTrack? by mutableStateOf(null)
  val track get() = _track

  private var _isPlaying: Boolean by mutableStateOf(false)
  val isPlaying get() = _isPlaying

  private var mediaPlayer: MediaPlayer? = null
  fun playTrack(track: SpotifyTrack) {
    // In case there's no preview url
    if (track.previewUrl == null) {
      println("Can' play this track : ${track.name}")
      return
    }

    _track = track
    println(_track!!.name)
    // If media player is not null, release resources
    mediaPlayer?.release()

    // init the new media player
    val newMediaPLayer = MediaPlayer()
    newMediaPLayer.setDataSource(track.previewUrl)

    val audioAttributes = AudioAttributes.Builder()
    audioAttributes.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
    newMediaPLayer.setAudioAttributes(audioAttributes.build())

    newMediaPLayer.setOnCompletionListener {
      _isPlaying = false
    }

    newMediaPLayer.prepare()
    newMediaPLayer.start()

    _isPlaying = true

    mediaPlayer = newMediaPLayer
  }

  fun togglePlayPause() {
    if (mediaPlayer == null) return

    if (mediaPlayer!!.isPlaying) {
      mediaPlayer!!.pause()
    } else {
      mediaPlayer!!.start()
    }

    _isPlaying = mediaPlayer!!.isPlaying
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackPlayer(viewModel: TrackPlayerViewModel) {
  val track = viewModel.track
  val isPlaying = viewModel.isPlaying

  LaunchedEffect(track) {
    println("Current Track : ${track?.name}")
  }

  AnimatedVisibility(
    visible = (track != null),
    modifier = Modifier.padding(16.dp)
  ) {
    Card(
      elevation = 7.dp, shape = RoundedCornerShape(5.dp),
      modifier = Modifier.fillMaxWidth().height(64.dp),
      backgroundColor = secondary
    ) {
      track!!
      val artists = track.artists.map { item -> item.name }


      Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        AsyncImage(
          modifier = Modifier.fillMaxHeight(),
          model = track.album.images.last().href,
          contentDescription = track.name
        )

        Column(Modifier.align(Alignment.CenterVertically).weight(1f)) {
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

        IconButton(
          modifier = Modifier.align(Alignment.CenterVertically)
            .padding(end = 12.dp),
          onClick = {
            viewModel.togglePlayPause()
          }) {
          Icon(
            if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
            "Toggle Play Pause"
          )
        }
      }
    }
  }
}
