package com.camarederic.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.camarederic.foodapp.R
import com.camarederic.foodapp.activities.MealActivity
import com.camarederic.foodapp.databinding.FragmentHomeBinding
import com.camarederic.foodapp.pojo.Meal
import com.camarederic.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

    companion object{
        const val MEAL_ID = "com.camarederic.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.camarederic.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.camarederic.foodapp.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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

        homeViewModel.getRandomMeal()

        observeRandomMeal()

        onRandomMealClick()
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
        homeViewModel.observeRandomMealLiveData()
            .observe(viewLifecycleOwner
            ) { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imageRandomMeal)

                this.randomMeal = meal
            }

    }


}