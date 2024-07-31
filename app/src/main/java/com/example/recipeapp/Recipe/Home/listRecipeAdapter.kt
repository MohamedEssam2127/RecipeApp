package com.example.recipeapp.Recipe.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Home.HomeFragment.Companion.userId
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class listRecipeAdapter(private val recipes: RecipeResponse ,val viewModel: FavoriteViewModel) :
    RecyclerView.Adapter<listRecipeAdapter.RecipesViewHolder>() {
    var onItemClick: ((Meal) -> Unit)? = null


    class RecipesViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        private var img: ImageView? = null
        private var title: TextView? = null

        val iconFav: ImageView = row.findViewById(R.id.favBtn)
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
        var isFavorite:Boolean =false
        CoroutineScope(Dispatchers.Main).launch {
            holder.iconFav.setImageResource(R.drawable.avorite)
             isFavorite = viewModel.isMealFavorite(recipes.meals[position].strMeal, userId)
            if (isFavorite) {
                holder.iconFav.setImageResource(R.drawable.baseline_favorite_24)
            }
        }


        holder.getTitle().text = recipes.meals[position].strMeal

        Glide.with(holder.itemView.context).load(recipes.meals[position].strMealThumb).apply(
            RequestOptions().placeholder(R.drawable.baseline_access_time_24)
                .error(R.drawable.baseline_assignment_late_24)
        ).into(holder.getImg())

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipes.meals[position])
        }
        holder.iconFav.setOnClickListener {
            val favoriteMeal = FavoriteMeal(
                idMeal = recipes.meals[position].idMeal.toInt(),
                strCategory=  recipes.meals[0].strCategory,
                strMeal= recipes.meals[0].strMeal,
                strMealThumb = recipes.meals[0].strMealThumb,
                strTags = recipes.meals[0].strTags,
                strYoutube= recipes.meals[0].strYoutube,
                userId= userId

            )
            if (!isFavorite) {
                viewModel.insertFavoriteMeal(favoriteMeal)
                holder.iconFav.setImageResource(R.drawable.baseline_favorite_24)
                Log.d("TAG", " is added to fav")
                isFavorite = true
            }else{
                viewModel.deleteFromFavList(favoriteMeal)
                holder.iconFav.setImageResource(R.drawable.avorite)
                Log.d("TAG", " is already fav")
                isFavorite = false
            }

        }

    }
//    fun checkIsFavorite (holder: RecipesViewHolder, position: Int){
//        viewModel.isMealFavorite(recipes.meals[position].strMeal)
//        var isFav = false
//        viewModel.isFav.observe(holder.itemView.context as LifecycleOwner) { isFavLive ->
//            Log.d("TAG", " is fav before  ${isFav}")
//            isFav = isFavLive
//            Log.d("TAG", " is fav after  ${isFav}")
//
//            if (isFavLive) {
//                holder.iconFav.setImageResource(R.drawable.baseline_favorite_24)
//            } else {
//
//                holder.iconFav.setImageResource(R.drawable.avorite)
//            }
//        }
//    }
}