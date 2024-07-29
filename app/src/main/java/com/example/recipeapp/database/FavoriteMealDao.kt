package com.example.recipeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

@Dao
interface FavoriteMealDao {

    @Insert
    suspend fun insertUser(FavoriteMeal: FavoriteMeal)
}