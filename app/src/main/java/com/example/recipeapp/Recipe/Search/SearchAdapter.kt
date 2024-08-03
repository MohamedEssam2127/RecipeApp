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

// RecyclerView.Adapter implementation for displaying a list of meals in a RecyclerView.
// This adapter handles the creation and binding of ViewHolder objects for the meal items.
class RecipeAdapter(private val onItemClick: (Meal) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    // List of Meal objects to be displayed by the adapter.
    private var meals: List<Meal> = listOf()

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return RecipeViewHolder(view, onItemClick)
    }

    // Called by RecyclerView to display the data at the specified position.
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    // Returns the total number of items in the data set held by the adapter.
    override fun getItemCount(): Int = meals.size

    // Updates the data set of the adapter with a new list of Meal objects and refreshes the views.
    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    // ViewHolder class for holding and binding the views for a meal item.
    class RecipeViewHolder(itemView: View, private val onItemClick: (Meal) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)

        // Binds the Meal object data to the views.
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
