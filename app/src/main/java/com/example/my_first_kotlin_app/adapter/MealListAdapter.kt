package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.MealLayoutAdapterBinding
import com.example.my_first_kotlin_app.model.Meal
import com.example.my_first_kotlin_app.utils.loadUrl

class MealListAdapter:RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

     lateinit var onItemClick  : ((Meal) -> Unit)

    inner class MealViewHolder(val binding: MealLayoutAdapterBinding) :RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(MealLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
       val currentList = differ.currentList[position]
        holder.binding.apply {
           tvMeal.text = currentList.strMeal
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList)
        }

        currentList.strMealThumb.let { holder.binding.ivMeal.loadUrl(it) }
    }
}