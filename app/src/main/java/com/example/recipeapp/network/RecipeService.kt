package com.example.recipeapp.network

import com.example.recipeapp.models.CategoriesResponse
import com.example.recipeapp.models.Meal
import com.example.recipeapp.models.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): Meal

    @GET("api/json/v1/1/search.php")
    suspend fun getMealsByFirstLetter(
        @Query("f") letter: String
    ): RecipeResponse

    @GET("api/json/v1/1/search.php")
    suspend fun searchMealByName(
        @Query("s") phrase: String
    ): RecipeResponse

    @GET("api/json/v1/1/categories.php")
    suspend fun listAllMealCategouris(): CategoriesResponse

    @GET("api/json/v1/1/filter.php?c=Seafood")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    )
}