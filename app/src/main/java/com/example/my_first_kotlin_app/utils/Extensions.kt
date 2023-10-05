package com.example.my_first_kotlin_app.utils


import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url:String?){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}