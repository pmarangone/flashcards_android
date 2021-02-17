package com.example.flashcards_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class AppActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

  private val db = Firebase.firestore
  private val usersRef = Firebase.firestore.collection("USERS")
  private val userId = Firebase.auth.currentUser?.uid.toString()

  var itemsList = mutableListOf<Deck>()
//  private val exampleList: List<Deck>()
//  private val adapter: ItemAdapter(exampleList)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_app)

    retrieveData()

    val myButton: Button = findViewById(R.id.btnLogout)
    myButton.setOnClickListener {
      FirebaseAuth.getInstance().signOut()
      Thread.sleep(500L)
      Intent(this, LoginActivity::class.java).also {
        startActivity(it)
      }
    }

    val btnUploadData: Button = findViewById(R.id.btnUploadData)
    btnUploadData.setOnClickListener {

      val name = etFirst.text.toString()
      val description = etSecond.text.toString()

      val deck = Deck(name, description, Calendar.getInstance().toString())
      etFirst.text = null
      etSecond.text = null
      saveData(deck)
    }

    btnRetrieveData.setOnClickListener { retrieveData() }
  }

  private fun saveData(deck: Deck) = CoroutineScope(Dispatchers.IO).launch {
    try {
      // add colection to document UserID
      val userDoc = usersRef.document("Patrick2")
      userDoc.collection("Decks").add(deck).await()

      val data = hashMapOf(
        "name" to "${deck.name}",
        "description" to "${deck.description}",
        "created" to "${deck.created}"
      )

      userDoc.collection(deck.name).document("deckInfo").set(data).await()

      retrieveData()
      withContext(Dispatchers.Main) {
        etFirst.text.clear()
        etSecond.text.clear()
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
//      var itemsList = mutableListOf<Deck>()

      val patri = Firebase.firestore.collection("USERS").document("Patrick2")
      val patriRef = patri.collection("Decks").get().await()

      itemsList.clear()

      for (document in patriRef.documents) {
        val deck = document.toObject<Deck>()
        itemsList.add(deck!!)
      }

      // allow to update activity
      withContext(Dispatchers.Main) {
        // todo: retrieve data from firestore
        val adapter = ItemAdapter(itemsList, this@AppActivity)
        var recyclerView: RecyclerView = findViewById(R.id.rvApp)

        recyclerView.apply {
          recyclerView.adapter = adapter
          recyclerView.layoutManager = LinearLayoutManager(this@AppActivity)
        }

        Toast.makeText(this@AppActivity, "Firestore reached", Toast.LENGTH_LONG).show()
      }

    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.makeText(this@AppActivity, "Error -> " + e.message, Toast.LENGTH_LONG).show()
      }
    }
  }

  override fun onItemClick(position: Int) {
    Toast.makeText(this, "RecyclerView onClickListener working", Toast.LENGTH_SHORT).show()

    for (i: Deck in itemsList) {
      Log.d("Debug", i.toString())
    }
    val adapter = ItemAdapter(itemsList, this@AppActivity)
    val clickedItem: Deck = itemsList[position]

    try {
      Intent(this, DeckInfo::class.java).also {
        it.putExtra("NAME", clickedItem.name)
        startActivity(it)
      }
    } catch (e: Exception) {
      Toast.makeText(this@AppActivity, "Error -> " + e.message, Toast.LENGTH_LONG).show()
    }
  }
}