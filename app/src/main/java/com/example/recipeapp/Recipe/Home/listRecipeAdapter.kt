package com.example.recipeapp.Recipe.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import kotlinx.coroutines.joinAll

class listRecipeAdapter(private val recipes: RecipeResponse) :
    RecyclerView.Adapter<listRecipeAdapter.RecipesViewHolder>() {

    var onItemClick: ((Meal) -> Unit)? = null

    class RecipesViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        private var img: ImageView? = null
        private var title: TextView? = null

        fun getImg(): ImageView {
            return img ?: row.findViewById(R.id.RecipeListImg)
        }

        fun getTitle(): TextView {
            return title ?: row.findViewById(R.id.RecipeListTitle)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_item, parent, false)
        return RecipesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return recipes.meals?.size?:0
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.getTitle().text = recipes.meals[position].strMeal

        Glide.with(holder.itemView.context).load(recipes.meals[position].strMealThumb).apply(
            RequestOptions().placeholder(R.drawable.baseline_access_time_24)
                .error(R.drawable.baseline_assignment_late_24)
        ).into(holder.getImg())

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipes.meals[position])
        }
    }
}