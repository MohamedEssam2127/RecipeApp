package com.example.recipeapp.Recipe.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.RecipeResponse
import com.example.recipeapp.network.RecipeRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel: ViewModel() {

    private val repo = RecipeRepository()

    private val _recipes = MutableLiveData<RecipeResponse>()
    val recipes = _recipes

    fun getRecipesByLetter(){
        viewModelScope.launch {
            var char = ('a'..'z').random().toString()  // randomize char every time
            _recipes.postValue(repo.getMealsByFirstLetter(char))
        }
    }
}