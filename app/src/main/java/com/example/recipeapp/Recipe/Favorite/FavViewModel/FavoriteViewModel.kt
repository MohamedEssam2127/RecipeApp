package com.example.recipeapp.Recipe.Favorite.FavViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepo
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel (private val repo: FavoriteRepo) : ViewModel() {

    private val _FavoriteMeal = MutableLiveData<List<UserWithFavorite>>()
    val FavoriteMeal: LiveData<List<UserWithFavorite>> get() = _FavoriteMeal

    private val _FavoritelistAdapter = MutableLiveData<List<FavoriteMeal>>()
    val FavoritelistAdapter: LiveData<List<FavoriteMeal>> get() = _FavoritelistAdapter

    private val _getByUserAndMeal = MutableLiveData<FavoriteMeal>()
    val getByUserAndMeal: LiveData<FavoriteMeal> get() = _getByUserAndMeal

    fun insertFavoriteMeal (meal: FavoriteMeal){
        viewModelScope.launch {
           repo.insertFavoriteMeal(meal)
        }
    }

    fun getFav(userId: Int) {
        viewModelScope.launch {
            val favoriteMeals = repo.getUserWithFavorite(userId)
            _FavoriteMeal.value = favoriteMeals

           _FavoritelistAdapter.value = favoriteMeals[0].favoriteMeals
            Log.d("FavoriteViewModel", "Favorite meals size: ${FavoritelistAdapter.value}")
        }
    }

    fun deleteFromFavList (favoriteMeal: FavoriteMeal){
        viewModelScope.launch {

            repo.deleteFavoriteMeal(favoriteMeal)
        }

    }

    fun getFavMealByUserIdAndMealId(userId: Int, mealId: Int){
        viewModelScope.launch {
           val favoriteMeal = repo.getFavoriteMealsByUserIdAndIdMeal(userId,mealId)
            _getByUserAndMeal.value = favoriteMeal

        }
    }

    suspend fun getFavMealByUserIdAndMealIdSync(userId: Int, mealId: Int) {
        return withContext(Dispatchers.IO) {
            // Perform the query on the background thread and return the result
            // Assume getFavMealByUserIdAndMealId() is a suspend function that returns the FavoriteMeal
            getFavMealByUserIdAndMealId(userId, mealId)
        }
    }
}