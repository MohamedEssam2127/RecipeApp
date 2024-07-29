package com.example.recipeapp.database.LocalDataBase

import com.example.recipeapp.models.Users

interface LocalDataBase {

    suspend fun insertUser(user: Users)

    suspend fun gellAllUsers(): List<Users>

    suspend fun getUserByEmailAndPassword(email: String, password: String): Users

    suspend fun isUserExist(email: String, password: String ): Boolean
}