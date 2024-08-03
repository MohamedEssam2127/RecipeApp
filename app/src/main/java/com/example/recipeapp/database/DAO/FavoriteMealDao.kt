package com.example.recipeapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal

@Dao
interface FavoriteMealDao {

    @Insert
    suspend fun insertFavoriteMeal(FavoriteMeal: FavoriteMeal)
    @Delete
    suspend fun deleteFavoriteMeal (FavoriteMeal: FavoriteMeal)

    @Transaction
    @Query("Select * from Users where userId=:userId")
    suspend fun getUserWithFavorite(userId:Int): List<UserWithFavorite>


    @Query ("SELECT EXISTS(SELECT * FROM FavoriteMeal WHERE strMeal = :id and userId =:uId)")
    suspend fun isMealFavorite(id:String,uId:Int):Boolean

    @Query ("SELECT * FROM FavoriteMeal  WHERE idMeal = :idMeal and userId =:uId ")
    suspend fun getFavoriteMealsByUserIdAndIdMeal (idMeal:Int, uId:Int):FavoriteMeal
}