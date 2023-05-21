@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(name = "Explore Screen", showBackground = true, showSystemUi = true)
@Composable
fun Explore() {
  var searchValue by remember { mutableStateOf(TextFieldValue("")) }

  Column(Modifier.padding(24.dp).fillMaxSize()) {
    Text("Explore", fontSize = 24.sp, fontWeight = FontWeight.Medium)
    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = searchValue,
      placeholder = { Text("Search") },
      shape = RoundedCornerShape(100),
      onValueChange = { searchValue = it })
  }
}
