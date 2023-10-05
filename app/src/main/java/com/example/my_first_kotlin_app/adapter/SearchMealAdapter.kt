package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.MealImageLayoutBinding
import com.example.my_first_kotlin_app.model.MealItem
import com.example.my_first_kotlin_app.utils.loadUrl

class SearchMealAdapter:RecyclerView.Adapter<SearchMealAdapter.SearchMealViewHolder>() {

    lateinit var onItemClick : ((MealItem) -> Unit)

    inner class SearchMealViewHolder(val binding : MealImageLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<MealItem>(){
        override fun areItemsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMealViewHolder {
        return SearchMealViewHolder(MealImageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SearchMealViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        holder.binding.tvMeal.text = currentList.strMeal
        currentList.strMealThumb.let { holder.binding.ivMeal.loadUrl(it) }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList)
        }
    }

}