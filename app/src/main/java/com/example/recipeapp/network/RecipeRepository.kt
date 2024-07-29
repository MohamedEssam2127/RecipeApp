package com.example.recipeapp.network

import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse

class RecipeRepository {

    private val api = RetrofitInstance.api

    suspend fun getRandomMeal(): Meal {
        return api.getRandomMeal()
    }

    suspend fun getMealsByFirstLetter(letter: String): RecipeResponse {
        return api.getMealsByFirstLetter(letter)
    }

    suspend fun searchMealByName(phrase: String): RecipeResponse {
        return api.searchMealByName(phrase)
    }

    suspend fun listAllMealCategories(): CategoriesResponse {
        return api.listAllMealCategouris()
    }

    suspend fun getMealsByCategory(category: String): RecipeResponse {
        return api.getMealsByCategory(category)
    }
}