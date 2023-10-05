package com.example.my_first_kotlin_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.R
import com.example.my_first_kotlin_app.activity.MealActivity
import com.example.my_first_kotlin_app.adapter.FavoriteMealAdapter
import com.example.my_first_kotlin_app.adapter.SwipeGesture
import com.example.my_first_kotlin_app.databinding.FragmentFavoriteBinding
import com.example.my_first_kotlin_app.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment() : Fragment(R.layout.fragment_favorite) {
    private var _binding : FragmentFavoriteBinding ?= null
    private val binding get() = _binding!!
    private val viewmodel : MealViewModel by viewModels()
    private lateinit var favAdapter : FavoriteMealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
        onItemClickFiveMeal()
        swipeGesture()
        observerGetAllFavMeal()


    }

    private fun setUpRv() {
        favAdapter = FavoriteMealAdapter()
        binding.rvFav.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = favAdapter
            setHasFixedSize(true)

        }

    }

    fun swipeGesture(){
        val swipeGseture = object : SwipeGesture(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        viewmodel.deleteMeal(favAdapter.differ.currentList[position])
                    }
                }
            }

        }
        val touchHelper = ItemTouchHelper(swipeGseture)
        touchHelper.attachToRecyclerView(binding.rvFav)
    }

    fun observerGetAllFavMeal(){
        viewmodel.getAllFavMeal().observe(viewLifecycleOwner){
            favAdapter.differ.submitList(it)
        }

    }
    fun onItemClickFiveMeal(){
        favAdapter.onItemClickFavMeal = {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(HomeFragment.idMeal,it.idMeal)
            intent.putExtra(HomeFragment.strMeal,it.strMeal)
            intent.putExtra(HomeFragment.strMealThumb,it.strMealThumb)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}