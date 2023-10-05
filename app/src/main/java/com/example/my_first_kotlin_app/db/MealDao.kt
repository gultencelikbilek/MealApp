package com.example.my_first_kotlin_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.my_first_kotlin_app.model.Category
import com.example.my_first_kotlin_app.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favMeal(meal:Meal)

    @Query(value = "SELECT * FROM meal ORDER BY idMeal DESC")
    fun getAllFavMeal() : LiveData<List<Meal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category:Category)

    @Query("DELETE FROM category")
    suspend fun clearDb()

    @Delete
    suspend fun deleteMeal(meal : Meal)
}