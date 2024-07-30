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
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repo: FavoriteRepo) : ViewModel() {

    private val _FavoriteMeal = MutableLiveData<List<UserWithFavorite>>()
    val FavoriteMeal: LiveData<List<UserWithFavorite>> get() = _FavoriteMeal
    fun insertFavoriteMeal (meal: FavoriteMeal){
        viewModelScope.launch {
            Log.d("FavoriteViewModel", "Inserting favorite meal: $meal")
           repo.insertFavoriteMeal(meal)
            Log.d("FavoriteViewModel", " done ")
        }
    }

    fun getFav(userId: Int) {
        viewModelScope.launch {
            val favoriteMeals = repo.getUserWithFavorite(userId)
            _FavoriteMeal.value = favoriteMeals
        }
    }
}