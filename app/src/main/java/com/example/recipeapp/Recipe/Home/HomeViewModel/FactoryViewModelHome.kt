package com.example.recipeapp.Recipe.Home.HomeViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Recipe.Home.Repo.RecipeRepository

class FactoryViewModelHome  (private val repo : RecipeRepository) :  ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repo) as T

        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}