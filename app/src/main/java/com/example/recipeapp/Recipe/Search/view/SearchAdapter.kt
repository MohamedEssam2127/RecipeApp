package com.example.recipeapp.Recipe.Search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal

/**
 * RecyclerView.Adapter implementation for displaying a list of meals in a RecyclerView.
 * This adapter handles the creation and binding of ViewHolder objects for the meal items.
 *
 * @property onItemClick A lambda function to handle item click events, providing the clicked Meal object.
 */
class RecipeAdapter(private val onItemClick: (Meal) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    /**
     * List of Meal objects to be displayed by the adapter.
     */
    private var meals: List<Meal> = listOf()

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * Inflates the item layout and creates a new RecipeViewHolder instance.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new RecipeViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return RecipeViewHolder(view, onItemClick)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * Binds the Meal object data to the views within the ViewHolder.
     *
     * @param holder The ViewHolder which should be updated to represent the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The number of items in the adapter's data set.
     */
    override fun getItemCount(): Int = meals.size

    /**
     * Updates the data set of the adapter with a new list of Meal objects and refreshes the views.
     *
     * @param newMeals The new list of Meal objects to be displayed.
     */
    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class for holding and binding the views for a meal item.
     *
     * @property itemView The root view of the item layout.
     * @property onItemClick A lambda function to handle item click events.
     */
    class RecipeViewHolder(itemView: View, private val onItemClick: (Meal) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)

        /**
         * Binds the Meal object data to the views.
         * Sets the meal name and image, and attaches a click listener to handle item clicks.
         *
         * @param meal The Meal object to be bound to the views.
         */
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
