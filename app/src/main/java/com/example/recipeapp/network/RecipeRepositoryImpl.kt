package com.example.recipeapp.network

import android.util.Log
import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse

/**
 * Implementation of the [RecipeRepository] interface.
 */
class RecipeRepositoryImpl : RecipeRepository {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "RecipeRepository"
    }

    override suspend fun getRandomMeal(): RecipeResponse {
        Log.d(TAG, "Requesting random meal")
        return api.getRandomMeal().also {
            Log.d(TAG, "Received random meal: $it")
        }
    }

    override suspend fun getMealsByFirstLetter(letter: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by first letter: $letter")
        return api.getMealsByFirstLetter(letter).also {
            Log.d(TAG, "Received meals by first letter: $it")
        }
    }

    override suspend fun searchMealByName(phrase: String): RecipeResponse {
        Log.d(TAG, "Searching meals by name: $phrase")
        return api.searchMealByName(phrase).also {
            Log.d(TAG, "Received search results: $it")
        }
    }

    override suspend fun listAllMealCategories(): CategoriesResponse {
        Log.d(TAG, "Requesting all meal categories")
        return api.listAllMealCategouris().also {
            Log.d(TAG, "Received meal categories: $it")
        }
    }

    override suspend fun getMealsByCategory(category: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by category: $category")
        return api.getMealsByCategory(category).also {
            Log.d(TAG, "Received meals by category: $it")
        }
    }

    override suspend fun getMealById(id: String): Meal {
        return api.getMealById(id).meals[0]
    }
}
