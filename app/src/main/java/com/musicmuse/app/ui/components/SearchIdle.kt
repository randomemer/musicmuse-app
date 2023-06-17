@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SearchIdle() {
  Column(
    Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
      Icon(
        Icons.Rounded.Search,
        "Error",
        modifier = Modifier.align(Alignment.CenterHorizontally).size(36.dp)
      )

      Text("Search something!")
    }
  }
}
