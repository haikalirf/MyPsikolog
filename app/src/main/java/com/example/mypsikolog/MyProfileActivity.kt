package com.example.mypsikolog

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.firebase.auth.FirebaseAuth

class MyProfileActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var logouttv : TextView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)

        auth = FirebaseAuth.getInstance()
        logouttv = findViewById<TextView>(R.id.tv_logoutBtn)
        logouttv.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            auth.signOut()
            startActivity(intent)
            finishAffinity()
        }

        var previousclick = findViewById<ConstraintLayout>(R.id.backPressMyProfile)
        previousclick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}