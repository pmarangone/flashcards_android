package com.example.flashcards_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

  private lateinit var auth: FirebaseAuth
  private lateinit var googleSignInClient: GoogleSignInClient
  private val RC_SIGN_IN = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    auth = Firebase.auth
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.default_web_client_id))
      .requestEmail()
      .build()
    googleSignInClient = GoogleSignIn.getClient(this, gso)

    val myButton: Button = findViewById(R.id.btn_second)
    myButton.setOnClickListener {
      signIn()
    }
  }

  private fun signIn() {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(data)
      try {
        // Google Sign In was successful, authenticate with Firebase
        val account = task.getResult(ApiException::class.java)!!
        Log.d("Debug", "firebaseAuthWithGoogle:" + account.id)
        firebaseAuthWithGoogle(account.idToken!!)
      } catch (e: ApiException) {
        // Google Sign In failed, update UI appropriately
        Log.w("Debug", "Google sign in failed", e)
      }
    }
  }

  private fun firebaseAuthWithGoogle(idToken: String) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          Log.d("Debug", "signInWithCredential:success")
          val user = auth.currentUser
          Intent(this, AppActivity::class.java).also {
            Thread.sleep(500L)
            startActivity(it)
            finish()
          }

        } else {
          Log.w("Debug", "signInWithCredential:failure", task.exception)
        }
      }
  }

}