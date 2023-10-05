package com.example.my_first_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_first_kotlin_app.databinding.FavoriteLayoutBinding
import com.example.my_first_kotlin_app.model.Meal
import com.example.my_first_kotlin_app.utils.loadUrl

class FavoriteMealAdapter:RecyclerView.Adapter<FavoriteMealAdapter.FavoriteMealViewHolder>() {

    lateinit var onItemClickFavMeal : ((Meal) -> Unit)

    inner class FavoriteMealViewHolder(val binding: FavoriteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

       private val difCallback = object : DiffUtil.ItemCallback<Meal>(){
           override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
               return oldItem.idMeal == newItem.idMeal
           }

           override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
               return oldItem == newItem
           }
       }

       val differ = AsyncListDiffer(this,difCallback)

    fun deleteItem(i :Int){
        differ.currentList.removeAt(i)
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        return FavoriteMealViewHolder(FavoriteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() =differ.currentList.size

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.binding.tvFavMeal.text = currentList.strMeal
        currentList.strMealThumb.let { holder.binding.ivFavMeal.loadUrl(it) }

        holder.itemView.setOnClickListener {
            onItemClickFavMeal.invoke(currentList)
        }
    }
}