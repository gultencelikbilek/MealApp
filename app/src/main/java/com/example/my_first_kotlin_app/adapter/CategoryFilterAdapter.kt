package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.MealImageLayoutBinding
import com.example.my_first_kotlin_app.model.Meal
import com.example.my_first_kotlin_app.utils.loadUrl

class CategoryFilterAdapter : RecyclerView.Adapter<CategoryFilterAdapter.CategoryFilterViewHolder>() {

    lateinit var onItemClick : ((Meal) -> Unit)

    inner class CategoryFilterViewHolder(val binding : MealImageLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterViewHolder {
        return CategoryFilterViewHolder(MealImageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryFilterViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.binding.tvMeal.text = currentList.strMeal

        currentList.strMealThumb.let { holder.binding.ivMeal.loadUrl(it) }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList)
        }
    }

}
