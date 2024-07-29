package com.example.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
   @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val userEmail: String,
    val userFirstName: String,
    val userSecondName: String,
    val userPassword: String
)
