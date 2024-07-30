package com.example.recipeapp.Recipe.Favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal

class FavoriteAdapter (
    private val values: List<FavoriteMeal>
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.favourite_row_view, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.text.text = values[position].strMeal

        Glide.with(holder.itemView.context).load(values[position].strMealThumb).into(holder.image)
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val image: ImageView = row.findViewById(R.id.SinglFav_Img)
        val iconFav: ImageView = row.findViewById(R.id.SinglFav_FavBttn)
        val text: TextView = row.findViewById(R.id.SinglFav_title)
    }
}