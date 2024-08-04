package com.example.recipeapp.Recipe.Search.repo

import com.example.recipeapp.models.RecipeResponse

/**
 * Interface defining the methods for fetching search results.
 */
interface SearchRepository {

    /**
     * Searches for meals by name from the API.
     *
     * @param phrase The name or phrase to search meals by.
     * @return [RecipeResponse] containing the search results for meals matching the phrase.
     */
    suspend fun searchMealByName(phrase: String): RecipeResponse
}
