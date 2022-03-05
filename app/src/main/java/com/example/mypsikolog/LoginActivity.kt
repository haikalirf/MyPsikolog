package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etUsernameLogin_activity_login)
        etPassword = findViewById(R.id.etPasswordLogin_activity_login)
        btnLogin = findViewById(R.id.btnLogin_activity_login)
        tvRegister = findViewById(R.id.tvRegister_activity_login)

        mAuth = FirebaseAuth.getInstance()
        
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            login(email, password)
//            val intent = Intent(this, MenuActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

    private fun login(email: String, password: String) {

    }
}