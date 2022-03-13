package com.example.mypsikolog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.name.text = currentUser.displayName
        val room = currentUser.uid + auth.currentUser?.uid
        dbRef.child("chats").child(room!!).child("messages").limitToLast(1).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    holder.lastChat.text = postSnapshot.getValue(Message::class.java)?.message
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.displayName)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val profilePicture = itemView.findViewById<ImageView>(R.id.ivProfilePicture_user_layout)
        val name = itemView.findViewById<TextView>(R.id.tvName_user_layout)
        val lastChat = itemView.findViewById<TextView>(R.id.tvLastChat_user_layout)
        val timeSinceLastChat = itemView.findViewById<TextView>(R.id.tvTimeSinceLastChat_user_layout)
    }
}