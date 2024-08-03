package com.example.recipeapp.Recipe.Favorite.Repo

import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal

interface FavoriteRepo {

    suspend fun insertFavoriteMeal(meal: FavoriteMeal)

    suspend fun getUserWithFavorite(userId:Int): List<UserWithFavorite>

    suspend fun deleteFavoriteMeal (favoriteMeal: FavoriteMeal)

//    suspend fun getFavoriteMealsByUserIdAndIdMeal(userId: Int, idMeal: Int): FavoriteMeal

    suspend fun isMealFavorite(id:String,uId:Int):Boolean

    suspend fun getFavoriteMealsByUserIdAndIdMeal (idMeal:Int, uId:Int):FavoriteMeal
}