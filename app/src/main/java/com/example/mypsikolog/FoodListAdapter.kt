package com.example.mypsikolog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FoodListAdapter (private val context : Activity, private val arrayList : ArrayList<FoodRecommendation>) : ArrayAdapter<FoodRecommendation>(context,
                    R.layout.listitem_foodrecommendation,arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.listitem_foodrecommendation,null)

        val imageView : ImageView = view.findViewById(R.id.iv_foodImage)
        val foodName : TextView = view.findViewById(R.id.tv_foodName)
        val foodInfo : TextView = view.findViewById(R.id.tv_foodInfo)

        imageView.setImageResource(arrayList[position].imageid)
        foodName.text = arrayList[position].foodName
        foodInfo.text = arrayList[position].info

        return view
    }
}