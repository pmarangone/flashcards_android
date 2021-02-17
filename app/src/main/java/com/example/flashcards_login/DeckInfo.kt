package com.example.flashcards_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_card_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DeckInfo : AppCompatActivity() {

  private val usersRef = Firebase.firestore.collection("USERS")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_card_info)

    val deckName: String = intent.getStringExtra("NAME").toString()
    retrieveData(deckName)

    btnCreateCard.setOnClickListener {
      if (etFront.text.isNullOrEmpty() || etBack.text.isNullOrEmpty()) {
        Toast.makeText(this, "Assert that all fields are filled", Toast.LENGTH_LONG).show()
      } else {
        var card = Card(
          etFront.text.toString(),
          etBack.text.toString()
        )
        addData(deckName, card)
      }
    }
    // todo: use deckname to visualize the docs of this deck
    btnLearn.setOnClickListener {
      Intent(this, DisplayCards::class.java).also {
        startActivity(it)
        finish()
      }
    }
  }

  private fun addData(deckName: String, card: Card) = CoroutineScope(Dispatchers.IO).launch {

    try {

      val userDoc = usersRef.document("Patrick2")
      userDoc.collection(deckName).add(card).await()

      withContext(Dispatchers.Main) {
        etFront.text.clear()
        etBack.text.clear()

        Toast.makeText(this@DeckInfo, "Successfully saved data", Toast.LENGTH_LONG).show()
      }

    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.makeText(this@DeckInfo, "Error on fn __addData__ -> " + e.message, Toast.LENGTH_LONG)
          .show()
      }
    }


  }

  private fun retrieveData(deckName: String) = CoroutineScope(Dispatchers.IO).launch {
    try {

      // todo: documentPath must be userID
      val patri = Firebase.firestore.collection("USERS").document("Patrick2")
      var deck: Deck = Deck()

      val docRef = patri.collection(deckName).document("deckInfo")
      docRef.get().addOnSuccessListener { documentSnapshot ->
        deck = documentSnapshot.toObject<Deck>()!!
        Log.d("Debug -> DeckInfo 1", deck.toString())
      }.await()

      withContext(Dispatchers.Main) {
        tvDeckName.text = deck.name.toString()
        tvDeckDescription.text = deck.description.toString()
        tvCreated.text = deck.created.toString()
        Toast.makeText(this@DeckInfo, "OK", Toast.LENGTH_LONG).show()
      }
    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.makeText(
          this@DeckInfo,
          "Error on fn __retrieveData__ -> " + e.message,
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }
}