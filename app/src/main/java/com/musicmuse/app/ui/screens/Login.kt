@file:Suppress("FunctionName")

package com.musicmuse.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(name = "LoginScreen")
@Composable
fun Login() {
  var email by remember { mutableStateOf(TextFieldValue("")) }
  var password by remember { mutableStateOf(TextFieldValue("")) }


  Scaffold { padding ->
    Box(Modifier.padding(padding).fillMaxHeight()) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(Modifier.padding(0.dp)) {
          Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            TextField(
              value = email,
              leadingIcon = { Icon(Icons.Rounded.Email, "Email") },
              placeholder = { Text("Email") },
              shape = RoundedCornerShape(100),
              onValueChange = { email = it },
              colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
              )
            )
            TextField(
              value = password,
              leadingIcon = { Icon(Icons.Rounded.Delete, "Password") },
              placeholder = { Text("Password") },
              shape = RoundedCornerShape(100),
              onValueChange = { password = it },
              colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
              )
            )
            Button(onClick = {}, shape = RoundedCornerShape(100)) {
              Text("LOGIN")
            }
          }
        }
      }
    }
  }
}
