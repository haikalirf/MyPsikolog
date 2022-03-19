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

class AppointmentAdapter(val context: Context, private val userList: ArrayList<User>): RecyclerView.Adapter<AppointmentAdapter.UserViewHolder>() {

    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    // creates a layout of users
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.appointment_layout, parent, false)
        return UserViewHolder(view)
    }

    // to input data into users
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.name.text = currentUser.displayName
        if (currentUser.chatPrice!! > 0) {
            holder.price.text = String.format("Rp. %,d.-", currentUser.chatPrice)
        } else {
            holder.price.text = "FREE"
        }
        holder.rating.text = currentUser.rating.toString()
        // clicking on the user
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AboutPsychologistActivity::class.java)

            intent.putExtra("nameAppointment", currentUser.displayName)
            intent.putExtra("uidAppointment", currentUser.uid)
            intent.putExtra("about",currentUser.about)
            intent.putExtra("address",currentUser.address)
            intent.putExtra("chatPrice",currentUser.chatPrice)
            intent.putExtra("appointmentPrice",currentUser.appointmentPrice)
            context.startActivity(intent)
        }
    }

    // gets the size of userList
    override fun getItemCount(): Int {
        return userList.size
    }

    // a view holder for user
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val profilePicture = itemView.findViewById<ImageView>(R.id.ivProfilePicture_appointment_layout)
        val name = itemView.findViewById<TextView>(R.id.tvName_appointment_layout)
        val price = itemView.findViewById<TextView>(R.id.tvPrice_appoinment_layout)
        val rating = itemView.findViewById<TextView>(R.id.tvRating_appointment_layout)
    }
}