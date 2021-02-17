package com.example.flashcards_login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_cards.*

class DisplayCards : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_display_cards)

    btnReview.setOnClickListener {
      tvBack.visibility = View.VISIBLE
      btnReview.visibility = View.INVISIBLE
      btnMinutes.visibility = View.VISIBLE
      btnDay.visibility = View.VISIBLE
      btnDays.visibility = View.VISIBLE
    }
  }
}