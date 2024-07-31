package com.example.recipeapp.Recipe.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal

class RecipeAdapter(private val onItemClick: (Meal) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var meals: List<Meal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return RecipeViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    class RecipeViewHolder(itemView: View, private val onItemClick: (Meal) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)

        fun bind(meal: Meal) {
            mealNameTextView.text = meal.strMeal
            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .placeholder(R.drawable.recipe)
                .into(mealImageView)

            itemView.setOnClickListener {
                onItemClick(meal)
            }
        }
    }
}
