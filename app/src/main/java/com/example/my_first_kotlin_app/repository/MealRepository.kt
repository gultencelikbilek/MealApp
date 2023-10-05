package com.example.my_first_kotlin_app.repository

import com.example.my_first_kotlin_app.api.MealApi
import com.example.my_first_kotlin_app.db.MealDatabase
import com.example.my_first_kotlin_app.model.Meal
import javax.inject.Inject

class MealRepository
@Inject constructor(private val api : MealApi, private val db : MealDatabase){

     fun getPopulerMeal(categoryName :String) = api.getPopulerMeal(categoryName)

     suspend fun getMealName(areaName :String) = api.getMealName(areaName)

     fun getSearchMeal(mealName :String) = api.getSearchMeal(mealName)

     fun getMeal(id:String) = api.getMeal(id)

     fun getCategoryMeal() = api.getCategoryMeal()

     fun getRandomMeal() = api.getRandomMeal()

     fun getCategoryFilter(categoryName : String) = api.getCategoryFilter(categoryName)

     suspend fun addFavMeal(meal:Meal) = db.mealDao().favMeal(meal)

     fun getAllFavMeal() = db.mealDao().getAllFavMeal()

      suspend fun deleteMeal(meal : Meal) = db.mealDao().deleteMeal(meal)

}