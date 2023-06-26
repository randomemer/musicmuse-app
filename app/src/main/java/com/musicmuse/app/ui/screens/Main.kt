@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.R
import com.musicmuse.app.models.AuthViewModel
import com.musicmuse.app.models.MainViewModel
import com.musicmuse.app.ui.components.BottomNav
import com.musicmuse.app.ui.components.Loading
import com.musicmuse.app.ui.components.TrackPlayer
import com.musicmuse.app.ui.config.AppTheme
import com.musicmuse.app.ui.nav.NavigationHost
import com.musicmuse.app.utils.AsyncState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
  val clientId = stringResource(R.string.spotify_client_id)
  val clientSecret = stringResource(R.string.spotify_client_secret)

  val viewModel: MainViewModel = viewModel(LocalActivity.current)
  val authViewModel = AuthViewModel()
  val navController = rememberNavController()

  LaunchedEffect(Unit) {
    viewModel.getSpotifyToken(clientId, clientSecret)
  }

  AppTheme {
    if (viewModel.currentUser != null) {
      Scaffold(
        bottomBar = { BottomNav(navController) },
        content = { paddingValues ->
          Box(Modifier.padding(paddingValues)) {
            when (viewModel.tokenStatus) {
              AsyncState.Pending -> Loading()

              AsyncState.Resolved -> {
                NavigationHost(navController)

                TrackPlayer(Modifier.align(Alignment.BottomCenter))
              }

              else -> {}
            }
          }
        }
      )
    } else Auth(authViewModel)
  }
}
