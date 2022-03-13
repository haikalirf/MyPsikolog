package com.example.mypsikolog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnChatButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChatButton = findViewById(R.id.btn_chatButton_activity_main)
        var ivUserIcon = findViewById<ImageView>(R.id.iv_userIcon)

        val buttonSuicideHotline = findViewById<Button>(R.id.btn_suicideHotline)
        buttonSuicideHotline.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:021500454")
            startActivity(intent)
        }

        btnChatButton.setOnClickListener {
            val intent = Intent(this, MainChatActivity::class.java)
            startActivity(intent)
        }

        ivUserIcon.setOnClickListener{
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }

    }
}

