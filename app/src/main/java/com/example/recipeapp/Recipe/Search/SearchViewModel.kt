package com.example.recipeapp.Recipe.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.Meal
import com.example.recipeapp.network.RecipeRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    // Repository for accessing the network layer
    private val repository = RecipeRepository()

    // LiveData to hold the list of meals
    val meals = MutableLiveData<List<Meal>>()

    // LiveData to hold error messages
    val errorMessage = MutableLiveData<String>()

    companion object {
        private const val TAG = "SearchViewModel"
    }

    // Function to search for meals by name
    fun searchMealByName(phrase: String) {
        Log.d(TAG, "Searching for meals with phrase: $phrase")
        viewModelScope.launch {
            try {
                // Call the repository to search for meals
                val response = repository.searchMealByName(phrase)

                if (response.meals.isEmpty()) {
                    // Post an error message if no meals are found
                    errorMessage.postValue("No meals found for '$phrase'")
                } else {
                    // Post the list of meals if found
                    meals.postValue(response.meals)
                }
                Log.d(TAG, "Search results received: ${response.meals}")
            } catch (e: Exception) {
                // Post an error message if an exception occurs
                Log.e(TAG, "Error searching for meals", e)
                errorMessage.postValue("Error searching for meals: ${e.message}")
            }
        }
    }
}
