package com.example.recipeapp.network

import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("random.php")
    suspend fun getRandomMeal(): RecipeResponse

    @GET("search.php")
    suspend fun getMealsByFirstLetter(
        @Query("f") letter: String
    ): RecipeResponse

    @GET("search.php")
    suspend fun searchMealByName(
        @Query("s") phrase: String
    ): RecipeResponse

    @GET("categories.php")
    suspend fun listAllMealCategouris(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ):RecipeResponse

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ):RecipeResponse

}