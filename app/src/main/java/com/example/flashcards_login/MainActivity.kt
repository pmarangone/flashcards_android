package com.example.flashcards_login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    auth = Firebase.auth
  }

  override fun onStart() {
    super.onStart()
    Handler(Looper.getMainLooper()).postDelayed({
      if (auth.currentUser == null) {
        Log.d("Debug", "User not logged in")
        Intent(this, LoginActivity::class.java).also {
          startActivity(it)
          finish()
        }
      } else {
        Intent(this, AppActivity::class.java).also {
          startActivity(it)
          finish()
        }
      }
    }, 2000)


  }
}


