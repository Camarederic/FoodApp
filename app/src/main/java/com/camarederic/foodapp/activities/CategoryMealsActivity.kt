package com.camarederic.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.camarederic.foodapp.R
import com.camarederic.foodapp.adapters.CategoryMealsAdapter
import com.camarederic.foodapp.databinding.ActivityCategoryMealsBinding
import com.camarederic.foodapp.fragments.HomeFragment
import com.camarederic.foodapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            mealsList.forEach {
                Log.d("test", it.strMeal)
                binding.tvCategoryCount.text = mealsList.size.toString() + " meals"
                categoryMealsAdapter.setMealList(mealsList)
            }
        })

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.recyclerViewMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}