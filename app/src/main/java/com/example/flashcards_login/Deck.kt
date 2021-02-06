package com.example.flashcards_login

import java.util.Calendar

data class Deck(
  var name: String = "",
  var description: String = "",
  val created: Calendar = Calendar.getInstance()
)
