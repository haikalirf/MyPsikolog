package com.example.mypsikolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypsikolog.databinding.ActivityMainBinding

class activity_lifestyle : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var foodArrayList : ArrayList<FoodRecommendation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifestyle)
        setContentView(binding.root)

        val imageid = intArrayOf(
            R.drawable.tomat, R.drawable.pisangkuning, R.drawable.milkfoodrecom, R.drawable.potatofoodrecom,
            R.drawable.seledri
        )

        val foodName = arrayOf(
            "Tomato",
            "Banana",
            "Milk",
            "Potato",
            "Celery"
        )

        val foodInfo = arrayOf(
            "27.1 kcal / 100 gram",
            "87.8 kcal / 100 gram",
            "46.3 kcal / 100 ml",
            "76.7 kcal / 100 gram",
            "33 kcal / 100 gram"
        )
        foodArrayList = ArrayList()
        for(i in foodName.indices) {
            val food = FoodRecommendation(foodName[i], foodInfo[i], imageid[i])
            foodArrayList.add(food)
        }

//        binding.listview.isClickable = true
//        binding.listview.adapter = FoodListAdapter(this,foodArrayList)
//        binding.listview.setOnClickListener(parent, view, position, id ->
//            val nama = foodName[position]
//            val info = foodInfo[position]
//            val imageID = imageid[position]
//
//            val i = Intent(this, listitem_foodrecommendation::class.java)
//            i.putExtra("name", nama)
//            i.putExtra("info", info)
//            i.putExtra("imageid",imageID)
//            startActivity(i)
//        )
    }
}