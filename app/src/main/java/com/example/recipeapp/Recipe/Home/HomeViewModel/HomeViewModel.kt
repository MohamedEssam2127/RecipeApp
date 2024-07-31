package com.example.recipeapp.Recipe.Home.HomeViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.RecipeResponse
import com.example.recipeapp.network.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val repo = RecipeRepository()

    private val _recipes = MutableLiveData<RecipeResponse>()
    val recipes = _recipes

    private val _randomMeal = MutableLiveData<RecipeResponse>()
    val randomMeal: LiveData<RecipeResponse> = _randomMeal
    fun getRecipesByLetter(){
        viewModelScope.launch {
            var char = ('a'..'z').random().toString()  // randomize char every time
            if (repo.getMealsByFirstLetter(char).meals == null){    // hit another char if no meals found with char
                getRecipesByLetter()
            }
            _recipes.postValue(repo.getMealsByFirstLetter(char))
        }
    }

    fun getRandomMeal(){
        viewModelScope.launch {
            _randomMeal.value=repo.getRandomMeal()
        }
    }
}