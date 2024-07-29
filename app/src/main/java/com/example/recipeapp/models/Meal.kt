package com.example.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strCategory: String,
    val strMeal: String,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String
)