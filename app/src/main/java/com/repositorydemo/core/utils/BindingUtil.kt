package com.repositorydemo.core.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.repositorydemo.core.base.adapter.BaseRecyclerAdapter


class BindingUtil {

    companion object {

        @JvmStatic
        @BindingAdapter(value = arrayOf("items"))
        fun configureAdapter(recyclerView: RecyclerView, items: List<*>) {
            try {
                val mLayoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.layoutManager = mLayoutManager
                val adapter = recyclerView.adapter as BaseRecyclerAdapter
                adapter.items = items
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                /*Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(view)*/
                Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
            }
        }
    }
}