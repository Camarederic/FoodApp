package com.camarederic.foodapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.camarederic.foodapp.R
import com.camarederic.foodapp.activities.MainActivity
import com.camarederic.foodapp.adapters.FavoritesMealsAdapter
import com.camarederic.foodapp.databinding.FragmentFavoritesBinding
import com.camarederic.foodapp.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesMealsAdapter()
        binding.recyclerViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealsLiveData()
            .observe(
                viewLifecycleOwner
            ) { meals ->
                meals.forEach {
                    Log.d("test", it.idMeal)
                }
                favoritesAdapter.differ.submitList(meals)
            }
    }

}