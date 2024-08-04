package com.example.recipeapp.Recipe.Search.repo

import android.util.Log
import com.example.recipeapp.models.RecipeResponse
import com.example.recipeapp.network.RetrofitInstance

/**
 * Implementation of the [SearchRepository] interface.
 */
class SearchRepositoryImpl : SearchRepository {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "SearchRepository"
    }

    /**
     * Searches for meals by name from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @param phrase The name or phrase to search meals by.
     * @return [RecipeResponse] containing the search results for meals matching the phrase.
     */
    override suspend fun searchMealByName(phrase: String): RecipeResponse {
        Log.d(TAG, "Searching meals by name: $phrase")
        return api.searchMealByName(phrase).also {
            Log.d(TAG, "Received search results: $it")
        }
    }
}