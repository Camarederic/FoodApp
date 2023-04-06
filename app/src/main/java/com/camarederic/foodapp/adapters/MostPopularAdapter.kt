package com.camarederic.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.camarederic.foodapp.databinding.PopularItemsBinding
import com.camarederic.foodapp.pojo.CategoryMeals

class MostPopularAdapter:RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    private var mealsList = ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals) -> Unit)

    class PopularMealViewHolder(var binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imagePopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    fun setMeals(mealsList:ArrayList<CategoryMeals>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
}