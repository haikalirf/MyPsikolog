package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etDisplayName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etDisplayName = findViewById(R.id.etDisplayNameRegister_activity_register)
        etEmail = findViewById(R.id.etEmailRegister_activity_register)
        etPassword = findViewById(R.id.etPasswordRegister_activity_register)
        btnRegister = findViewById(R.id.btnRegister_activity_register)
        tvLogin = findViewById(R.id.tvLogin_activity_register)

        auth = FirebaseAuth.getInstance()
        
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        btnRegister.setOnClickListener {
            val displayName = etDisplayName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            signUp(displayName, email, password)
        }
    }

    private fun signUp(displayName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(displayName, email, auth.currentUser?.uid!!)
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(displayName: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().reference

        dbRef.child("user").child(uid).setValue(User(displayName, email, uid))
    }
}