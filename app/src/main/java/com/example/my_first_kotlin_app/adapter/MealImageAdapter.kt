package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.MealImageLayoutBinding
import com.example.my_first_kotlin_app.model.MealItem
import com.example.my_first_kotlin_app.utils.loadUrl

class MealImageAdapter :RecyclerView.Adapter<MealImageAdapter.PopulerViewHolder>() {

    lateinit var onItemClick : ((MealItem) -> Unit)

    var onLongItemClick : ((MealItem) ->Unit) ?= null

    inner class PopulerViewHolder(val binding : MealImageLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object :DiffUtil.ItemCallback<MealItem>(){
        override fun areItemsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealItem, newItem: MealItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulerViewHolder {
        return PopulerViewHolder(MealImageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: PopulerViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        currentList.strMealThumb.let { holder.binding.ivMeal.loadUrl(it) }

        holder.binding.tvMeal.visibility = View.INVISIBLE
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList)
        }
          holder.itemView.setOnLongClickListener {
              onLongItemClick!!.invoke(currentList)
              true
          }

    }
}


