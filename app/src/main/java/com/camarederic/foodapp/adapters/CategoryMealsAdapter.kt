package com.camarederic.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.camarederic.foodapp.databinding.MealItemBinding
import com.camarederic.foodapp.pojo.MealList
import com.camarederic.foodapp.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()

    fun setMealList(mealList: List<MealsByCategory>) {
        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imageMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}