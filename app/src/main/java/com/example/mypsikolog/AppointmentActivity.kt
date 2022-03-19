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
import java.util.*
import kotlin.collections.ArrayList

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
        var freeOn: Boolean = false
        var paidOn: Boolean = false
        var ratingOn: Boolean = false
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

        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    dbRef.child("user").addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (postSnapshot in snapshot.children) {
                                val currentUser = postSnapshot.getValue(User::class.java)
                                if (currentUser?.isPsikolog == true && currentUser.displayName!!.lowercase(Locale.getDefault()).contains(searchText)) {
                                    userList.add(currentUser)
                                }
                            }
                            appointmentAdapter.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
                } else {
                    dbRef.child("user").addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (postSnapshot in snapshot.children) {
                                val currentUser = postSnapshot.getValue(User::class.java)
                                if (currentUser?.isPsikolog == true) {
                                    userList.add(currentUser)
                                }
                            }
                            appointmentAdapter.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
                }
                return false
            }
        })

        free.setOnClickListener {
            if (freeOn) {
                free.setBackgroundResource(R.drawable.empty_background)
                freeOn = false
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
            } else {
                freeOn = true
                free.setBackgroundResource(R.drawable.on_background)
                if (paidOn) {
                    paidOn = false
                    paid.setBackgroundResource(R.drawable.empty_background)
                }
                if (ratingOn) {
                    ratingOn = false
                    rating.setBackgroundResource(R.drawable.empty_background)
                }
                dbRef.child("user").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (postSnapshot in snapshot.children) {
                            val currentUser = postSnapshot.getValue(User::class.java)
                            if (currentUser?.isPsikolog == true && currentUser.chatPrice == 0) {
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

        paid.setOnClickListener {
            if (paidOn) {
                paid.setBackgroundResource(R.drawable.empty_background)
                paidOn = false
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
            } else {
                paidOn = true
                paid.setBackgroundResource(R.drawable.on_background)
                if (freeOn) {
                    freeOn = false
                    free.setBackgroundResource(R.drawable.empty_background)
                }
                if (ratingOn) {
                    ratingOn = false
                    rating.setBackgroundResource(R.drawable.empty_background)
                }
                dbRef.child("user").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (postSnapshot in snapshot.children) {
                            val currentUser = postSnapshot.getValue(User::class.java)
                            if (currentUser?.isPsikolog == true && currentUser.chatPrice!! > 0) {
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

        rating.setOnClickListener {
            if (ratingOn) {
                rating.setBackgroundResource(R.drawable.empty_background)
                ratingOn = false
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
            } else {
                ratingOn = true
                rating.setBackgroundResource(R.drawable.on_background)
                if (paidOn) {
                    paidOn = false
                    paid.setBackgroundResource(R.drawable.empty_background)
                }
                if (freeOn) {
                    freeOn = false
                    free.setBackgroundResource(R.drawable.empty_background)
                }
                dbRef.child("user").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (postSnapshot in snapshot.children) {
                            val currentUser = postSnapshot.getValue(User::class.java)
                            if (currentUser?.isPsikolog == true && currentUser.rating!! > 4) {
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
    }
}