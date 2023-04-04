package com.camarederic.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camarederic.foodapp.pojo.Meal
import com.camarederic.foodapp.pojo.MealList
import com.camarederic.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response != null) {
                    val randomMeal:Meal = response.body()!!.meals[0]
                    Log.d("TEST", "meal id : ${randomMeal.idMeal} meal : ${randomMeal.strMeal}")
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }
}