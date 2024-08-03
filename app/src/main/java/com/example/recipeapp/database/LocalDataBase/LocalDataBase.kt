package com.example.recipeapp.database.LocalDataBase

import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

interface LocalDataBase {

    suspend fun insertUser(user: Users)

    suspend fun getUserByEmailAndPassword(email: String, password: String): Users

    suspend fun  isEmailExist(email: String): Boolean

    // Fav
    suspend fun insertFavoriteMeal(meal: FavoriteMeal)

    suspend fun getUserWithFavorite(userId:Int): List<UserWithFavorite>

    suspend fun deleteFavoriteMeal (FavoriteMeal: FavoriteMeal)


    suspend fun isMealFavorite(id:String,uId:Int):Boolean

    suspend fun getFavoriteMealsByUserIdAndIdMeal (idMeal:Int, uId:Int):FavoriteMeal
}