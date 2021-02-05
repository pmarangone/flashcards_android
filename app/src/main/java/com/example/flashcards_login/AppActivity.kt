package com.example.flashcards_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppActivity : AppCompatActivity() {

  private val deckRef = Firebase.firestore.collection("t1")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_app)

    retrieveData();

    val myButton: Button = findViewById(R.id.btnLogout)
    myButton.setOnClickListener {
      FirebaseAuth.getInstance().signOut();
      Thread.sleep(500L)
      Intent(this, LoginActivity::class.java).also {
        startActivity(it)
        finish();
      }
    }

    val btnUploadData: Button = findViewById(R.id.btnUploadData)
    btnUploadData.setOnClickListener {
      val name = etFirst.text.toString()
      val description = etSecond.text.toString()

      val deck = Deck(name, description)
      etFirst.setText(null)
      etSecond.setText(null)
      saveData(deck)


    }

//    btnRetrieveData.setOnClickListener { retrieveData() }
  }

  private fun saveData(deck: Deck) = CoroutineScope(Dispatchers.IO).launch {
    try {
      deckRef.add(deck).await()
      withContext(Dispatchers.Main) {
        Toast.makeText(this@AppActivity, "Successfully saved data", Toast.LENGTH_LONG).show()
      }

    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.makeText(this@AppActivity, e.message, Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun retrieveData() = CoroutineScope(Dispatchers.IO).launch {
    try {
      val querySnapshot = deckRef.get().await()
      val sbOne = StringBuilder()
      val sbTwo = StringBuilder()

      for (document in querySnapshot.documents) {
        val deck = document.toObject<Deck>()
        val name = deck?.name
        val description = deck?.description

        sbOne.append("$name\n")
        sbTwo.append("$description\n")
      }

    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.makeText(this@AppActivity, e.message, Toast.LENGTH_LONG).show()
      }
    }
  }


}