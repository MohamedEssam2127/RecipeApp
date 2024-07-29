package com.example.recipeapp.network

import android.util.Log
import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse

class RecipeRepository {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "RecipeRepository"
    }

    suspend fun getRandomMeal(): Meal {
        Log.d(TAG, "Requesting random meal")
        return api.getRandomMeal().also {
            Log.d(TAG, "Received random meal: $it")
        }
    }

    suspend fun getMealsByFirstLetter(letter: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by first letter: $letter")
        return api.getMealsByFirstLetter(letter).also {
            Log.d(TAG, "Received meals by first letter: $it")
        }
    }

    suspend fun searchMealByName(phrase: String): RecipeResponse {
        Log.d(TAG, "Searching meals by name: $phrase")
        return api.searchMealByName(phrase).also {
            Log.d(TAG, "Received search results: $it")
        }
    }

    suspend fun listAllMealCategories(): CategoriesResponse {
        Log.d(TAG, "Requesting all meal categories")
        return api.listAllMealCategouris().also {
            Log.d(TAG, "Received meal categories: $it")
        }
    }

    suspend fun getMealsByCategory(category: String): RecipeResponse {
        Log.d(TAG, "Requesting meals by category: $category")
        return api.getMealsByCategory(category).also {
            Log.d(TAG, "Received meals by category: $it")
        }
    }
}
