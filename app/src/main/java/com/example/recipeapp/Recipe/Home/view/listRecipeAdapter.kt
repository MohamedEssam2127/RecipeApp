package com.example.recipeapp.Recipe.Home.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Home.view.HomeFragment.Companion.userId
import com.example.recipeapp.Recipe.Home.HomeViewModel.HomeViewModel
import com.example.recipeapp.Recipe.RecipeActivity
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import com.example.recipeapp.Recipe.Home.Repo.RecipeRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class listRecipeAdapter(private val activity: RecipeActivity,private val recipes: RecipeResponse ,val viewModel: FavoriteViewModel) :
    RecyclerView.Adapter<listRecipeAdapter.RecipesViewHolder>() {
    var onItemClick: ((Meal) -> Unit)? = null
    val homeViewModel = HomeViewModel(RecipeRepositoryImpl())

    class RecipesViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        private var img: ImageView? = null
        private var title: TextView? = null

        val iconFav: ImageView = row.findViewById(R.id.Details_favBtn)
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
        CoroutineScope(Dispatchers.IO).launch {

             isFavorite = viewModel.isMealFavorite(recipes.meals[position].strMeal, userId)
            withContext(Dispatchers.Main) {
                holder.iconFav.setImageResource(R.drawable.avorite)
                if (isFavorite) {
                    holder.iconFav.setImageResource(R.drawable.baseline_favorite_24)
                }
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
                viewModel.viewModelScope.launch {
                    //hit the Api to get the complete Recipe object
                    val clickedMeal = homeViewModel.getMealById(recipes.meals[position].idMeal)
                    val favoriteMeal = FavoriteMeal(
                        idMeal = clickedMeal.idMeal.toInt(),
                        strCategory = clickedMeal.strCategory,
                        strMeal = clickedMeal.strMeal,
                        strMealThumb =clickedMeal.strMealThumb,
                        strTags = clickedMeal.strTags,
                        strYoutube = clickedMeal.strYoutube,
                        userId = userId,
                        strArea = clickedMeal.strArea,
                        strInstructions = clickedMeal.strInstructions
                    )
                    if (!isFavorite) {
                        viewModel.insertFavoriteMeal(favoriteMeal)
                        holder.iconFav.setImageResource(R.drawable.baseline_favorite_24)
                        Log.d("TAG", " is added to fav")
                        isFavorite = true
                    } else {
                        activity.showRemoveFavDialog(favoriteMeal) {
                            viewModel.deleteFromFavList(favoriteMeal)
                            holder.iconFav.setImageResource(R.drawable.avorite)
                            Log.d("TAG", " is already fav")
                            isFavorite = false
                        }
                    }
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