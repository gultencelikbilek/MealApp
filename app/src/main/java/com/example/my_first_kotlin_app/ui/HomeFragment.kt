package com.example.my_first_kotlin_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_first_kotlin_app.R
import com.example.my_first_kotlin_app.activity.CategoryActivity
import com.example.my_first_kotlin_app.activity.MealActivity
import com.example.my_first_kotlin_app.adapter.*
import com.example.my_first_kotlin_app.databinding.FragmentHomeBinding
import com.example.my_first_kotlin_app.model.Meal
import com.example.my_first_kotlin_app.ui.bottomsheet.BottomSheetFragment
import com.example.my_first_kotlin_app.utils.loadUrl
import com.example.my_first_kotlin_app.viewmodel.MainMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding : FragmentHomeBinding ?=null
    private val binding get() = _binding!!
    private lateinit var randomMeal :Meal
    private lateinit var populerAdapter : MealImageAdapter
    private lateinit var categoryAdapter : CategoryAdapter
    private  val viewmodel : MainMealViewModel by viewModels()

    companion object{
      const val  idMeal = "com.example.easyfood.fragment.idMeal"
      const val  strMeal = "com.example.easyfood.fragment.strMeal"
      const val  strMealThumb = "com.example.easyfood.fragment.strMealThumb"
      const val  categoryName  = "com.example.easyfood.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpRv()
        viewmodel.getPopulerMeal()
        observerPopulerMeal()
        populerItemClickListener()
        populerLongItemClick()




        setUpCategoryRv()
        viewmodel.getCategoryMeal()
        observerCategoryMeal()
        categoryItemClick()

        viewmodel.getRandomMeal()
        observerRandomMeal()
        randomMealOnClick()





    }

    private fun setUpRv() {
        populerAdapter = MealImageAdapter()
        binding.rvMealImage.apply {
            adapter = populerAdapter
            layoutManager  = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }

    fun observerPopulerMeal(){
        viewmodel.populerMeal.observe(viewLifecycleOwner){
            populerAdapter.differ.submitList(it)
        }
    }

    fun populerItemClickListener(){
        populerAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(idMeal,it.idMeal)
            intent.putExtra(strMeal,it.strMeal)
            intent.putExtra(strMealThumb,it.strMealThumb)
            startActivity(intent)

        }
    }


    fun populerLongItemClick(){
        populerAdapter.onLongItemClick = {
            val mealBottomSheet = BottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheet.show(childFragmentManager,"Meal Info")
        }
    }





    fun setUpCategoryRv(){
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = categoryAdapter
            setHasFixedSize(true)
        }

    }

    fun observerCategoryMeal(){
        viewmodel.categoryMeal.observe(viewLifecycleOwner){
            categoryAdapter.differ.submitList(it)
        }
    }

    fun categoryItemClick(){
        categoryAdapter.onItemClick = {
            val intent = Intent(activity,CategoryActivity::class.java)
            intent.putExtra(categoryName,it.strCategory)
            startActivity(intent)

        }
    }

    fun observerRandomMeal(){
        viewmodel.randomMeal.observe(viewLifecycleOwner){
            it.strMealThumb.let { binding.ivRandomMeal.loadUrl(it) }

            this.randomMeal = it
        }
    }
    fun randomMealOnClick(){
        binding.cardRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(idMeal,randomMeal.idMeal)
            intent.putExtra(strMeal,randomMeal.strMeal)
            intent.putExtra(strMealThumb,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}