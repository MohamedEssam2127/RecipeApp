package com.example.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMeal")
data class FavoriteMeal(
    @PrimaryKey(autoGenerate = true)
    val idMeal: Int=0,
    val strCategory: String,
    val strMeal: String,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String,
    val userId: Int
)
