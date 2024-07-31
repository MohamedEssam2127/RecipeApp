package com.example.recipeapp.Recipe.Favorite.FavViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepo

class FavoriteViewModelFactory(private val favRepo :FavoriteRepo) :  ViewModelProvider.Factory {



    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(this.favRepo) as T

        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}