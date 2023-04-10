package com.camarederic.foodapp.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.camarederic.foodapp.R
import com.camarederic.foodapp.activities.MainActivity
import com.camarederic.foodapp.activities.MealActivity
import com.camarederic.foodapp.databinding.FragmentMealBottomSheetBinding
import com.camarederic.foodapp.fragments.HomeFragment
import com.camarederic.foodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(HomeFragment.MEAL_ID, mealId)
                intent.putExtra(HomeFragment.MEAL_NAME, mealName)
                intent.putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                startActivity(intent)
            }

        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData()
            .observe(viewLifecycleOwner, Observer { meal ->
                Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.imageBottomSheet)
                binding.tvSheetBottomArea.text = meal.strArea
                binding.tvSheetBottomCategory.text = meal.strCategory
                binding.tvBottomSheetMealName.text = meal.strMeal

                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
            })

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}