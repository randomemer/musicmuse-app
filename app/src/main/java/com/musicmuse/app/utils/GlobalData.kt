package com.musicmuse.app.utils

object GlobalData {
  private val map = mutableMapOf<String, Any>()

  fun <T : Any> get(key: String): T {
    return map[key] as T
  }

  fun <T : Any> put(key: String, value: T) {
    map[key] = value
  }
}
