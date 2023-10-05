package com.example.my_first_kotlin_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_first_kotlin_app.model.*
import com.example.my_first_kotlin_app.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainMealViewModel
@Inject constructor(private val repository: MealRepository) :ViewModel(){

   private val populerMutableLiveData = MutableLiveData<List<MealItem>>()
    val populerMeal : LiveData<List<MealItem>>
    get() = populerMutableLiveData

    private val mealNameMutableLiveData = MutableLiveData<List<MealItem>>()
    val mealName :LiveData<List<MealItem>>
    get() = mealNameMutableLiveData

    private val searchMealMutableLiveData = MutableLiveData<List<MealItem>>()
    val searchMeal : LiveData<List<MealItem>>
    get() = searchMealMutableLiveData

    private val categoryMealMutableLiveData = MutableLiveData<List<Category>>()
    val categoryMeal : LiveData<List<Category>>
    get() = categoryMealMutableLiveData

    private val categoryFilterMealMutableLiveData = MutableLiveData<List<Meal>>()
    val categoryMealFilter : LiveData<List<Meal>>
    get() = categoryFilterMealMutableLiveData

    private val randomMealMutableLiveData = MutableLiveData<Meal>()
    val randomMeal : LiveData<Meal>
    get() = randomMealMutableLiveData

    fun getPopulerMeal()  = viewModelScope.launch {
        repository.getPopulerMeal(categoryName = "Seafood").enqueue(object : Callback<PopularMeal>{
            override fun onResponse(call: Call<PopularMeal>, response: Response<PopularMeal>) {
                if (response.body() != null){
                    populerMutableLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<PopularMeal>, t: Throwable) {
                Log.d("Meal ViewModel",t.message.toString())
            }

        })
    }

    fun getSearchMeal(mealName  :String) = viewModelScope.launch {
        repository.getSearchMeal(mealName).enqueue(object : Callback<SearchMeal>{
            override fun onResponse(call: Call<SearchMeal>, response: Response<SearchMeal>) {
                response.body().let {
                    searchMealMutableLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<SearchMeal>, t: Throwable) {
               Log.d("Meal ViewModel",t.message.toString())
            }

        })
    }

    fun getCategoryMeal() = viewModelScope.launch {
        repository.getCategoryMeal().enqueue(object : Callback<CategoryMeal>{
            override fun onResponse(call: Call<CategoryMeal>, response: Response<CategoryMeal>) {
               response.body().let {
                   categoryMealMutableLiveData.value= response.body()!!.categories
               }
            }

            override fun onFailure(call: Call<CategoryMeal>, t: Throwable) {
                Log.d("Meal ViewModel",t.message.toString())
            }

        })
    }

    fun getCategoryMealFilter(categoryName : String) = viewModelScope.launch {
        repository.getCategoryFilter(categoryName).enqueue(object: Callback<CategoryFilter>{
            override fun onResponse(
                call: Call<CategoryFilter>,
                response: Response<CategoryFilter>
            ) {
                response.body().let {
                    categoryFilterMealMutableLiveData.value =it!!.meals


                }
            }

            override fun onFailure(call: Call<CategoryFilter>, t: Throwable) {
                Log.d("Meal ViewModel",t.message.toString())
            }

        })
    }



    fun getRandomMeal() = viewModelScope.launch {
        repository.getRandomMeal().enqueue(object : Callback<RandomMeal>{
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
               if (response.body() != null){
                   randomMealMutableLiveData.value = response.body()!!.meals[0]
               }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.d("Meal ViewModel",t.message.toString())
            }

        })
    }



}