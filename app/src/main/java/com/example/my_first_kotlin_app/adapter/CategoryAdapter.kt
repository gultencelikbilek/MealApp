package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.CategoryAdapterBinding
import com.example.my_first_kotlin_app.utils.loadUrl
import com.example.my_first_kotlin_app.model.Category


class CategoryAdapter :RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    lateinit var onItemClick : ((Category) -> Unit)

    inner class CategoryViewHolder(val binding : CategoryAdapterBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return CategoryViewHolder(CategoryAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.binding.tvCategoryName.text = currentList.strCategory

        currentList.strCategoryThumb.let { holder.binding.ivMeal.loadUrl(it) }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentList)
        }

    }
}