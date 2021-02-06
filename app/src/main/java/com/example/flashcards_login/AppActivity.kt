package com.example.flashcards_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.protobuf.LazyStringArrayList
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppActivity : AppCompatActivity() {

  private lateinit var auth: FirebaseAuth
  private val usersRef = Firebase.firestore.collection("USERS")
  private val userId = Firebase.auth.currentUser?.uid.toString()

  lateinit var recyclerView: RecyclerView;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_app)

//    retrieveData();

    var itemsList = mutableListOf(
      Deck("A", "A"),
      Deck("B", "B"),
      Deck("C", "C"),
      Deck("C", "C"),
      Deck("C", "C"),
      Deck("C", "C"),
      Deck("C", "C"),
      Deck("C", "C"),
    )

    // todo: retrieve data from firestore
    recyclerView = findViewById(R.id.rvApp)

    val adapter = ItemAdapter(itemsList)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

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
      // add colection to document UserID
      val userDoc = usersRef.document(userId)

      userDoc.collection(deck.name).add(deck).await()
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
      val querySnapshot = usersRef.get().await()
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