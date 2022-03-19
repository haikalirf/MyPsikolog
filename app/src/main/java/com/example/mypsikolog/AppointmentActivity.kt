package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.google.firebase.database.*

class AppointmentActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var search: SearchView
    private lateinit var free: Button
    private lateinit var paid: Button
    private lateinit var rating: Button
    private lateinit var psychologistRecyclerView: RecyclerView
    private lateinit var appointmentAdapter: AppointmentAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        backButton = findViewById(R.id.btnBack_activity_appointment)
        search = findViewById(R.id.svSearch_activity_appointment)
        free = findViewById(R.id.btnFree_activity_appointment)
        paid = findViewById(R.id.btnPaid_activity_appointment)
        rating = findViewById(R.id.btnRating_activity_appointment)
        userList = ArrayList()
        psychologistRecyclerView = findViewById(R.id.rvPsychologist_activity_appointment)
        appointmentAdapter = AppointmentAdapter(this, userList)
        psychologistRecyclerView.layoutManager = LinearLayoutManager(this)
        psychologistRecyclerView.adapter = appointmentAdapter
        dbRef = FirebaseDatabase.getInstance().reference

        // intent to move to main chat activity
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // adds users to the userList using the firebase database
        dbRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (currentUser?.isPsikolog == true) {
                        userList.add(currentUser)
                    }
                }
                appointmentAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}