@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComponent(error: String) {
  Column(
    Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
      Icon(
        Icons.Rounded.Error,
        "Error",
        modifier = Modifier.align(Alignment.CenterHorizontally).size(36.dp)
      )

      Text(error)
    }
  }
}
