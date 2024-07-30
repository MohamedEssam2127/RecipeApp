package com.example.recipeapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.recipeapp.models.FavoriteMeal

@Dao
interface FavoriteMealDao {

    @Insert
    suspend fun insertUser(FavoriteMeal: FavoriteMeal)
    @Delete
    suspend fun deleteFavoriteMeal (FavoriteMeal: FavoriteMeal)

}