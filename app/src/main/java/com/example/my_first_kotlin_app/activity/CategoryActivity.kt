package com.example.my_first_kotlin_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_first_kotlin_app.adapter.CategoryFilterAdapter
import com.example.my_first_kotlin_app.databinding.ActivityCategoryBinding
import com.example.my_first_kotlin_app.ui.HomeFragment
import com.example.my_first_kotlin_app.viewmodel.MainMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryFilterAdapter
    private val viewmodel: MainMealViewModel by viewModels()

    companion object{
        const val  idMeal = "com.example.easyfood.fragment.idMeal"
        const val  strMeal = "com.example.easyfood.fragment.strMeal"
        const val  strMealThumb = "com.example.easyfood.fragment.strMealThumb"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRv()
        viewmodel.getCategoryMealFilter(intent.getStringExtra(HomeFragment.categoryName)!!)
        observerCategoryFilter()
        categoryItemClick()

    }

    private fun observerCategoryFilter() {
        viewmodel.categoryMealFilter.observe(this){
            categoryAdapter.differ.submitList(it)
        }
    }

    private fun setUpRv() {
        categoryAdapter = CategoryFilterAdapter()
        binding.rvCategory.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
    }


    fun categoryItemClick(){
        categoryAdapter.onItemClick = {
            val intent = Intent(this,MealActivity::class.java)
            intent.apply {
                putExtra(idMeal,it.idMeal)
                putExtra(strMeal,it.strMeal)
                putExtra(strMealThumb,it.strMealThumb)
            }
            startActivity(intent)
        }
    }
}