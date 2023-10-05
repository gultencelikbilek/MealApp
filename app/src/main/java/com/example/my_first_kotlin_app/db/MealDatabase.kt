package com.example.my_first_kotlin_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.my_first_kotlin_app.model.Category
import com.example.my_first_kotlin_app.model.Meal



@Database(entities = [Meal::class,Category::class], version = 3)
abstract class MealDatabase :RoomDatabase(){

    abstract  fun mealDao() : MealDao

}