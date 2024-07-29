package com.example.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
   @PrimaryKey val userId: String,
    val userEmail: String,
    val userFirstName: String,
    val userSecondName: String,
    val userPassword: String
)
