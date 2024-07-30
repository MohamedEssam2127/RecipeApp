package com.example.recipeapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strCategory: String,
    val strMeal: String,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String,
    val strArea: String,
    val strInstructions: String
): Parcelable