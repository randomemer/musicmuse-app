package com.musicmuse.app.utils

fun welcomeMessage(hour: Int): String {
  if (hour < 12) {
    return "Good Morning"
  } else if (hour < 6) {
    return "Good Afternoon"
  } else {
    return "Good Evening"
  }
}
