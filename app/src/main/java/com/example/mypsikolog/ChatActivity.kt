package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var textInput: EditText
    private lateinit var sendButton: ImageView
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var currentUser: TextView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbRef: DatabaseReference

    private var receiverRoom: String? = null
    private var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        backButton = findViewById(R.id.ibBack_activity_chat)
        textInput = findViewById(R.id.etTextInput_activity_chat)
        sendButton = findViewById(R.id.ivSendButton_activity_chat)
        chatRecyclerView = findViewById(R.id.rvChatBubble_activity_chat)
        currentUser = findViewById(R.id.tvCurrentUser_activity_chat)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        dbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        currentUser.text = name

        backButton.setOnClickListener {
            val intent = Intent(this, MainChatActivity::class.java)
            startActivity(intent)
            finish()
        }

        dbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (postSnapshot in snapshot.children) {
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        sendButton.setOnClickListener {
            val message = textInput.text.toString()
            val sdf = SimpleDateFormat("hh:mm aa dd/MM/yy")
            val currentTime = sdf.format(Date())
            val messageObject = Message(message, currentTime, senderUid)

            dbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                dbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }
            textInput.setText("")
        }
    }
}