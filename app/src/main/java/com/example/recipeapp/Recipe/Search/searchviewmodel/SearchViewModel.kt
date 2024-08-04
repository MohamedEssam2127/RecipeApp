package com.example.recipeapp.Recipe.Search.searchviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Recipe.Search.repo.SearchRepository
import com.example.recipeapp.models.Meal
import kotlinx.coroutines.launch

/**
 * ViewModel class for handling search functionality within the recipe search feature.
 * Manages the state and data for meal search operations and communicates with the repository.
 *
 * @property repository The SearchRepository instance used to perform meal search operations.
 */
class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    /**
     * LiveData object for holding a list of Meal objects returned from the search operation.
     */
    val meals = MutableLiveData<List<Meal>>()

    /**
     * LiveData object for holding an error message in case the search operation fails or no results are found.
     */
    val errorMessage = MutableLiveData<String>()

    /**
     * Initiates a search for meals based on the provided search phrase.
     * Uses coroutine scope to perform the search operation asynchronously.
     *
     * @param phrase The search phrase used to find meals.
     */
    fun searchMealByName(phrase: String) {
        viewModelScope.launch {
            try {
                // Perform the search operation via the repository
                val response = repository.searchMealByName(phrase)

                // Update the LiveData with the results or an error message
                if (response.meals.isEmpty()) {
                    errorMessage.postValue("No meals found for '$phrase'")
                } else {
                    meals.postValue(response.meals)
                }
            } catch (e: Exception) {
                errorMessage.postValue("Error searching for meals: ${e.message}")
            }
        }
    }
}
