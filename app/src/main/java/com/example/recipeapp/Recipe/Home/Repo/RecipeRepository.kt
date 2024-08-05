package com.example.recipeapp.Recipe.Home.Repo

import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse

/**
 * Interface defining the methods for fetching recipes and meal data.
 */
interface RecipeRepository {

    /**
     * Fetches a random meal from the API.
     *
     * @return [RecipeResponse] containing the details of the random meal.
     */
    suspend fun getRandomMeal(): RecipeResponse

    /**
     * Fetches meals that start with the specified letter from the API.
     *
     * @param letter The first letter of the meal names to fetch.
     * @return [RecipeResponse] containing meals starting with the specified letter.
     */
    suspend fun getMealsByFirstLetter(letter: String): RecipeResponse

    /**
     * Searches for meals by name from the API.
     *
     * @param phrase The name or phrase to search meals by.
     * @return [RecipeResponse] containing the search results for meals matching the phrase.
     */
    suspend fun searchMealByName(phrase: String): RecipeResponse

    /**
     * Lists all meal categories from the API.
     *
     * @return [CategoriesResponse] containing all available meal categories.
     */
    suspend fun listAllMealCategories(): CategoriesResponse

    /**
     * Fetches meals by a specific category from the API.
     *
     * @param category The category of meals to fetch.
     * @return [RecipeResponse] containing meals in the specified category.
     */
    suspend fun getMealsByCategory(category: String): RecipeResponse

    /**
     * Fetches a meal by its ID from the API.
     *
     * @param id The ID of the meal to fetch.
     * @return [Meal] containing the details of the meal with the specified ID.
     */
    suspend fun getMealById(id: String): Meal
}
