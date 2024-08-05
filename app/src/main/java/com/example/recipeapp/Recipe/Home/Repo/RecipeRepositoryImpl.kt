package com.example.recipeapp.Recipe.Home.Repo

import android.util.Log
import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import com.example.recipeapp.network.RetrofitInstance

/**
 * Implementation of the [RecipeRepository] interface.
 */
class RecipeRepositoryImpl : RecipeRepository {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "RecipeRepository"
    }

    /**
     * Fetches a random meal from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @return [RecipeResponse] containing the details of the random meal.
     */
    override suspend fun getRandomMeal(): RecipeResponse {
        Log.d(TAG, "Requesting random meal")
        return api.getRandomMeal().also {
            Log.d(TAG, "Received random meal: $it")
        }
    }

    /**
     * Fetches meals that start with the specified letter from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @param letter The first letter of the meal names to fetch.
     * @return [RecipeResponse] containing meals starting with the specified letter.
     */
    override suspend fun getMealsByFirstLetter(letter: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by first letter: $letter")
        return api.getMealsByFirstLetter(letter).also {
            Log.d(TAG, "Received meals by first letter: $it")
        }
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

    /**
     * Lists all meal categories from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @return [CategoriesResponse] containing all available meal categories.
     */
    override suspend fun listAllMealCategories(): CategoriesResponse {
        Log.d(TAG, "Requesting all meal categories")
        return api.listAllMealCategouris().also {
            Log.d(TAG, "Received meal categories: $it")
        }
    }

    /**
     * Fetches meals by a specific category from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @param category The category of meals to fetch.
     * @return [RecipeResponse] containing meals in the specified category.
     */
    override suspend fun getMealsByCategory(category: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by category: $category")
        return api.getMealsByCategory(category).also {
            Log.d(TAG, "Received meals by category: $it")
        }
    }

    /**
     * Fetches a meal by its ID from the API.
     *
     * Logs the request and response details for debugging purposes.
     *
     * @param id The ID of the meal to fetch.
     * @return [Meal] containing the details of the meal with the specified ID.
     */
    override suspend fun getMealById(id: String): Meal {
        return api.getMealById(id).meals[0]
    }
}
