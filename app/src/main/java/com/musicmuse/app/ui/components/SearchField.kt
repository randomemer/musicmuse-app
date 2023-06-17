@file:Suppress("FunctionName")

package com.musicmuse.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.musicmuse.app.ui.screens.SearchViewModel

@Composable
fun SearchField(viewModel: SearchViewModel) {
  TextField(
    modifier = Modifier.fillMaxWidth(),
    value = viewModel.searchValue,
    singleLine = true,
    placeholder = { Text("Search") },
    shape = RoundedCornerShape(10),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = {
      viewModel.search()
    }),
    onValueChange = { viewModel.searchValue = it },
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = Color.Transparent,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    ),
    trailingIcon = {
      if (viewModel.searchValue.text.isEmpty()) return@TextField

      IconButton(onClick = {
        viewModel.searchValue = TextFieldValue("")
      }) {
        Icon(Icons.Rounded.Close, "Close Search")
      }
    }
  )
}