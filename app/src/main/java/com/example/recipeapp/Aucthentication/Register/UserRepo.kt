package com.example.recipeapp.Aucthentication.Register

import com.example.recipeapp.database.LocalDataBase.LocalDataBase
import com.example.recipeapp.models.Users

interface UserRepo{
    suspend fun insertUser(user: Users)
    suspend fun isUserExist(email: String, password: String ): Boolean
}