package com.example.mypsikolog

import android.os.Bundle
import android.renderscript.Sampler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainChatActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.rvChatList_activity_main)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        dbRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var psikologStatus: Boolean? = false
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid == currentUser?.uid) {
                        psikologStatus = currentUser?.isPsikolog
                    }
                }
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid != currentUser?.uid && psikologStatus != currentUser?.isPsikolog) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}