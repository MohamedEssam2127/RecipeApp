package com.example.recipeapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
   @PrimaryKey(autoGenerate = true)
   val userId: Int = 0,
   @ColumnInfo(name = "Email")
    val userEmail: String,
   @ColumnInfo(name = "FirstName")
    val userFirstName: String,
   @ColumnInfo(name = "SecondName")
    val userSecondName: String,
   @ColumnInfo(name = "Password")
    val userPassword: String
)
