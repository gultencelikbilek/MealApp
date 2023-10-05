package com.example.my_first_kotlin_app.api

import com.example.my_first_kotlin_app.model.*
import com.example.my_first_kotlin_app.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET(Constants.END_POINT1)
     fun getPopulerMeal(@Query(value = "c") categoryName :String) :Call<PopularMeal>

     @GET(Constants.END_POINT1)
     suspend fun getMealName(@Query(value = "a")areaName :String ) :Response<MealNameItem>

     @GET(Constants.END_POINT2)
     fun getSearchMeal(@Query(value ="s")mealName : String) : Call<SearchMeal>

     @GET(Constants.END_POINT3)
     fun getMeal(@Query(value = "i")id:String) :Call<MealDetail>

     @GET(Constants.END_POINT4)
     fun getCategoryMeal() : Call<CategoryMeal>

     @GET(Constants.END_POINT5)
     fun getRandomMeal() : Call<RandomMeal>

     @GET(Constants.END_POINT1)
     fun getCategoryFilter(@Query(value = "c") categoryName :String) : Call<CategoryFilter>



}


