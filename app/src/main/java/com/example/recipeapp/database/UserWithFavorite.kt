package com.example.recipeapp.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

data class UserWithFavorite(
    @Embedded val user: Users,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val favoriteMeals: List<FavoriteMeal>
)
