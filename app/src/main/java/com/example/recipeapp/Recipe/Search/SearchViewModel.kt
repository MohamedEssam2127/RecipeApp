package com.example.recipeapp.Recipe.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.Meal
import com.example.recipeapp.network.RecipeRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = RecipeRepository()
    val meals = MutableLiveData<List<Meal>>()
    val errorMessage = MutableLiveData<String>()

    companion object {
        private const val TAG = "SearchViewModel"
    }

    fun searchMealByName(phrase: String) {
        Log.d(TAG, "Searching for meals with phrase: $phrase")
        viewModelScope.launch {
            try {
                val response = repository.searchMealByName(phrase)
                if (response.meals.isNullOrEmpty()) {
                    errorMessage.postValue("No meals found for '$phrase'")
                } else {
                    meals.postValue(response.meals)
                }
                Log.d(TAG, "Search results received: ${response.meals}")
            } catch (e: Exception) {
                Log.e(TAG, "Error searching for meals", e)
                errorMessage.postValue("Error searching for meals: ${e.message}")
            }
        }
    }
}
