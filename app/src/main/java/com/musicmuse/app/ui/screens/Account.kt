@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.LocalActivity
import com.musicmuse.app.models.MainViewModel


@Composable
fun Account() {
  val mainViewModel: MainViewModel = viewModel(LocalActivity.current)
  val googleData =
    mainViewModel.currentUser?.providerData?.find { it.providerId == "google.com" }
      ?: return

  Column(
    Modifier.fillMaxSize().padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(9.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      AsyncImage(
        googleData.photoUrl,
        googleData.displayName,
        modifier = Modifier.size(156.dp).clip(RoundedCornerShape(100))
      )
      Text(
        "${googleData.displayName}",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
      )
      Text("${googleData.email}", style = MaterialTheme.typography.bodyMedium)
    }

    Button(onClick = {
      Firebase.auth.signOut()
    }) {
      Text("LOGOUT", fontWeight = FontWeight.Bold)
    }
  }
}
