package com.example.flashcards_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_third)

        val myButton: Button = findViewById(R.id.btn_third)
        myButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            Thread.sleep(500L)
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish();
            }
        }

    }
}