package com.example.recipeapp.Recipe.RecipeDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R

class RecipeDetailImagesAdapter(private val images: List<String>): RecyclerView.Adapter<RecipeDetailImagesAdapter.RecipeDetailImagesViewHolder>() {

    class RecipeDetailImagesViewHolder(private val row: View): RecyclerView.ViewHolder(row) {
        private var img: ImageView? = null

        fun getImg(): ImageView {
            return img ?: row.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailImagesViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.details_recipe_images_row, parent, false)
        return RecipeDetailImagesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return images.size?:0
    }

    override fun onBindViewHolder(holder: RecipeDetailImagesViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(images[position]).apply(
            RequestOptions().placeholder(R.drawable.baseline_access_time_24)
                .error(R.drawable.baseline_assignment_late_24)
        ).into(holder.getImg())

    }
}