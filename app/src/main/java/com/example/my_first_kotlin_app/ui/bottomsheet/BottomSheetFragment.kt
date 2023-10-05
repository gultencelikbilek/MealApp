package com.example.my_first_kotlin_app.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.my_first_kotlin_app.R
import com.example.my_first_kotlin_app.activity.MealActivity
import com.example.my_first_kotlin_app.databinding.FragmentBottomSheetBinding
import com.example.my_first_kotlin_app.ui.HomeFragment
import com.example.my_first_kotlin_app.utils.loadUrl
import com.example.my_first_kotlin_app.viewmodel.BottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment :BottomSheetDialogFragment(){

    private var _binding : FragmentBottomSheetBinding ?= null
    private val binding get() = _binding!!
    private var mealId: String ?= null
    private val viewmodel : BottomSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let{
            viewmodel.getBottomSheetMeal(it)
        }

       observerBotttomSheetMeal()
        onClickBottomSheet()



    }

    private var mealName:String?=null
    private var mealThumb:String?=null
    private fun observerBotttomSheetMeal() {
        viewmodel.bottomSheetMeal.observe(viewLifecycleOwner) {
            it.strMealThumb.let { binding.ivMeal.loadUrl(it) }

            binding.apply {
                tvBottomArea.text = it.strArea
                tvBottomCategories.text = it.strCategory
                tvBottomSheetMealName.text = it.strMeal

            }
            mealName = it.strMeal
            mealThumb = it.strMealThumb

        }

    }

    fun onClickBottomSheet(){
        binding.bottomSheet.setOnClickListener {
            val intent = Intent( activity, MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.idMeal,mealId)
                putExtra(HomeFragment.strMeal,mealName)
                putExtra(HomeFragment.strMealThumb,mealThumb)
            }
            startActivity(intent)
        }
    }




    companion object{
        const val MEAL_ID = "param1"
        fun newInstance(param1 :String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID,param1)
                }
            }
    }

}