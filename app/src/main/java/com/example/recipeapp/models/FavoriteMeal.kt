package com.example.recipeapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Users::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "FavoriteMeal"
)

data class FavoriteMeal(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val idMeal: Int ,
    val strCategory: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val userId: Int,
    val strArea: String,
    val strInstructions: String
)
