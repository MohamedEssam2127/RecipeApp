package com.example.recipeapp.Aucthentication.AuthRepository

import com.example.recipeapp.models.Users

interface UserRepo{
    suspend fun insertUser(user: Users)
    suspend fun isUserExist(email: String, password: String ): Boolean
    suspend fun getUserByEmailAndPassword(email: String, password: String): Users
    suspend fun isEmailExist(email: String): Boolean
}