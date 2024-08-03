package com.example.recipeapp.network

import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse

/**
 * Interface defining the methods for fetching recipes and meal data.
 */
interface RecipeRepository {
    suspend fun getRandomMeal(): RecipeResponse
    suspend fun getMealsByFirstLetter(letter: String): RecipeResponse
    suspend fun searchMealByName(phrase: String): RecipeResponse
    suspend fun listAllMealCategories(): CategoriesResponse
    suspend fun getMealsByCategory(category: String): RecipeResponse
    suspend fun getMealById(id: String): Meal
}
