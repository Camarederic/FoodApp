package com.camarederic.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.camarederic.foodapp.activities.CategoryMealsActivity
import com.camarederic.foodapp.activities.MainActivity
import com.camarederic.foodapp.activities.MealActivity
import com.camarederic.foodapp.adapters.CategoriesAdapter
import com.camarederic.foodapp.adapters.MostPopularAdapter
import com.camarederic.foodapp.databinding.FragmentHomeBinding
import com.camarederic.foodapp.pojo.MealsByCategory
import com.camarederic.foodapp.pojo.Meal
import com.camarederic.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.camarederic.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.camarederic.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.camarederic.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.camarederic.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()

        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }


    private fun preparePopularItemsRecyclerView() {
        binding.recyclerViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onRandomMealClick() {
        binding.imageRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData()
            .observe(
                viewLifecycleOwner
            ) { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imageRandomMeal)

                this.randomMeal = meal
            }

    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData()
            .observe(
                viewLifecycleOwner
            ) { mealList ->
                popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
            }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData()
            .observe(
                viewLifecycleOwner
            ) { categories ->
                categories.forEach { category ->
                    Log.d("test", "${category.strCategory}")
                    categoriesAdapter.setCategoryList(categories)
                }
            }
    }


}