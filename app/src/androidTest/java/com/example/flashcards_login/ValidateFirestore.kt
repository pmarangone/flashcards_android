package com.example.flashcards_login

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValidateFirestore {

  @Test
  fun createDecks() {
    val db = Firebase.firestore
    val ref = db.collection("Test")
    val content = hashMapOf(
      "name" to "Test",
      "content" to "This is a test",
      "type" to "Private"
    )

    try {
      ref.add(content)
      Log.d("Tag: Success", "DocumentSnapshot test added")

    } catch (e: Exception) {
      Log.w("Tag: Error", "Error adding test document", e)
    }
  }


}