package com.example.my_first_kotlin_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?
)