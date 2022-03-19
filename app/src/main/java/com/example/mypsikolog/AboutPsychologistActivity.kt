package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class AboutPsychologistActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var about: TextView
    private lateinit var address: TextView
    private lateinit var chatPrice: TextView
    private lateinit var appointmentPrice: TextView
    private lateinit var backButton: ImageButton
    private lateinit var btnAppointment: Button
    private lateinit var btnChat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_psychologist)

        name = findViewById(R.id.tvName_activity_about_psychologist)
        about = findViewById(R.id.tvAbout_activity_about_psychologist)
        address = findViewById(R.id.tvAddress_activity_about_psychologist)
        chatPrice = findViewById(R.id.tvOnlineChatPrice_activity_about_psychologist)
        appointmentPrice = findViewById(R.id.tvAppointmentPrice_activity_about_psychologist)
        btnAppointment = findViewById(R.id.btnAppointment_activity_about_psychologist)
        btnChat = findViewById(R.id.btnChat_activity_about_psychologist)
        backButton = findViewById(R.id.btnBack_activity_about_psychologist)

        name.text = intent.getStringExtra("nameAppointment")
        about.text = intent.getStringExtra("about")
        address.text = intent.getStringExtra("address")
        if (intent.getIntExtra("chatPrice", 0) > 0) {
            chatPrice.text = String.format("Rp. %,d.-", intent.getIntExtra("chatPrice", 0))
        } else {
            chatPrice.text = "Free"
        }
        if (intent.getIntExtra("appointmentPrice", 0) > 0) {
            appointmentPrice.text = String.format("Rp. %,d.-", intent.getIntExtra("appointmentPrice", 0))
        } else {
            appointmentPrice.text = "Free"
        }

        backButton.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }

        btnChat.setOnClickListener {
            val uid = intent.getStringExtra("uidAppointment")
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("name", intent.getStringExtra("name"))
            intent.putExtra("uidChat", uid)
            this.startActivity(intent)
        }
    }
}