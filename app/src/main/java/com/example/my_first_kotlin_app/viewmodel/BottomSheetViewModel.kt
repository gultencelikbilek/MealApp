package com.example.my_first_kotlin_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_first_kotlin_app.model.MealDetail
import com.example.my_first_kotlin_app.model.MealItem
import com.example.my_first_kotlin_app.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel
@Inject constructor(private val repository: MealRepository) :ViewModel(){

    private val bottomSheetMutableLiveData = MutableLiveData<MealItem>()
    val bottomSheetMeal : LiveData<MealItem>
    get() = bottomSheetMutableLiveData

    fun getBottomSheetMeal(id:String) = viewModelScope.launch {
        repository.getMeal(id).enqueue(object : Callback<MealDetail>{
            override fun onResponse(call: Call<MealDetail>, response: Response<MealDetail>) {
                if (response.body() != null){
                    bottomSheetMutableLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealDetail>, t: Throwable) {
               Log.d("BottomSheet ViewModel",t.message.toString())
            }

        })
    }
}