package com.example.my_first_kotlin_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_first_kotlin_app.R
import com.example.my_first_kotlin_app.activity.MealActivity
import com.example.my_first_kotlin_app.adapter.SearchMealAdapter
import com.example.my_first_kotlin_app.databinding.FragmentSearchBinding
import com.example.my_first_kotlin_app.viewmodel.MainMealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding : FragmentSearchBinding ?= null
    private val binding get() = _binding!!
    private lateinit var searchAdapter : SearchMealAdapter
    private val viewmodel : MainMealViewModel by viewModels()
    companion object{
        const val  idMeal = "com.example.easyfood.fragment.idMeal"
        const val  strMeal = "com.example.easyfood.fragment.strMeal"
        const val  strMealThumb = "com.example.easyfood.fragment.strMealThumb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchJob()
        setUpSearchRv()
        observerSearchViewModel()
        mealOnItemClick()
    }


    private fun searchJob(){
        var job : Job?=null
        binding.etSearch.addTextChangedListener {text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (text.toString().isNotEmpty()){
                        viewmodel.getSearchMeal(text.toString())
                    }
                }
            }
        }




    }
    fun setUpSearchRv(){
        searchAdapter = SearchMealAdapter()
        binding.rvSearchMeal.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
            setHasFixedSize(true)
        }

    }

    fun observerSearchViewModel(){
        viewmodel.searchMeal.observe(viewLifecycleOwner){
            searchAdapter.differ.submitList(it)
        }
    }

    fun mealOnItemClick() {
        searchAdapter.onItemClick ={
            val intent = Intent(requireContext(),MealActivity::class.java)
            intent.apply {
                putExtra(idMeal,it.idMeal)
                putExtra(strMeal,it.strMeal)
                putExtra(strMealThumb,it.strMealThumb)
            }
            startActivity(intent)
        }
    }

}