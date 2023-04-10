package com.camarederic.foodapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.camarederic.foodapp.activities.MainActivity
import com.camarederic.foodapp.adapters.FavoritesMealsAdapter
import com.camarederic.foodapp.databinding.FragmentFavoritesBinding
import com.camarederic.foodapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


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

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deleteMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(deleteMeal)
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deleteMeal)
                    }
                ).show()
                favoritesAdapter.differ.currentList[position]
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewFavorites)
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