package com.musicmuse.app.utils

object GlobalData {
  private val map = mutableMapOf<String, String>()

  fun get(key: String): String? {
    return map[key]
  }

  fun put(key: String, value: String) {
    map[key] = value
  }
}
