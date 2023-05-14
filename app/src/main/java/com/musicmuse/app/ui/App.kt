package com.musicmuse.app.ui

import androidx.compose.runtime.Composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.musicmuse.app.ui.screens.Home
import com.musicmuse.app.ui.screens.Login

@Composable
fun App() {
    val auth = Firebase.auth
    if (auth.currentUser == null) {
        Login()
    } else {
        Home()
    }
}