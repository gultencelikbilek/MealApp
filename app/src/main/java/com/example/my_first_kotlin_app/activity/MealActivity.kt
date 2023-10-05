package com.example.my_first_kotlin_app.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.my_first_kotlin_app.R
import com.example.my_first_kotlin_app.databinding.ActivityMealBinding
import com.example.my_first_kotlin_app.model.Meal
import com.example.my_first_kotlin_app.model.MealItem
import com.example.my_first_kotlin_app.ui.HomeFragment
import com.example.my_first_kotlin_app.utils.loadUrl
import com.example.my_first_kotlin_app.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {
    private lateinit var idMeal :String
    private lateinit var strMeal :String
    private lateinit var strMealThumb :String
    private lateinit var youtubeLink : String
    private var isLiked = false

    private val viewmodel : MealViewModel by viewModels()
    private lateinit var binding : ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)



        getInformationMainActivity()
        setInformationView()
        loadingCase()


        viewmodel.getMeal(idMeal)
        observerMeal()

        onYoutubeClick()

        fab()
    }

    fun fab(){
        binding.fabFavorite.setOnClickListener {
            isLiked = !isLiked
            if (isLiked){
                val meal = Meal(idMeal.toInt(),idMeal,strMeal,strMealThumb)
                viewmodel.addFavMeal(meal)
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()


            }else{
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)

            }

        }
    }


    private fun getInformationMainActivity() {
        val intent = intent
        idMeal = intent.getStringExtra(HomeFragment.idMeal)!!
        strMeal = intent.getStringExtra(HomeFragment.strMeal)!!
        strMealThumb = intent.getStringExtra(HomeFragment.strMealThumb)!!
    }

    fun setInformationView(){
        binding.ivMealDetail.loadUrl(strMealThumb)

        binding.collapsingToolbar.title = strMeal
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun loadingCase() {
        binding.tvInstructions.visibility = View.INVISIBLE
    }

    fun observerMeal(){
        viewmodel.mealResponse.observe(this, object : Observer<MealItem>{
            override fun onChanged(t: MealItem?) {
                val meal = t

                binding.tvInstructionStep.text = meal!!.strInstructions
                youtubeLink = meal.strYoutube.toString()

            }

        })

    }

    fun onYoutubeClick(){
       binding.ivYouTube.setOnClickListener {
           val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }


}