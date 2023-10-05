package com.example.my_first_kotlin_app.di

import android.content.Context
import androidx.room.Room
import com.example.my_first_kotlin_app.api.MealApi
import com.example.my_first_kotlin_app.db.MealDatabase
import com.example.my_first_kotlin_app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @get:Provides
    val api : MealApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMealDatabase(
       @ApplicationContext  context :Context
    ) = Room.databaseBuilder(
        context,
        MealDatabase::class.java,
        "meal_db"
    ).build()


}