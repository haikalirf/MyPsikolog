package com.example.mypsikolog

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnChatButton: ImageButton
    private lateinit var reminderTV: TextView
    private val CHANNEL_ID = "channel_ID_01"
    private val notificationid = 101

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

        createNotificationChannel()
        reminderTV.setOnClickListener{
            sendNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, SplashScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo_no_text)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo_text)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_no_text)
            .setContentTitle("My Psikolog")
            .setContentText("Description")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Tarik nafas tenangkan pikiran sejenak dan bersyukurlah anda masih dapat melihat betapa indahnya dunia hari ini"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationid, builder.build())
        }
    }
}

