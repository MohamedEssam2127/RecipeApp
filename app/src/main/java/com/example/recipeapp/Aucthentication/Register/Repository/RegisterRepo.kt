package com.example.recipeapp.Aucthentication.Register.Repository

import com.example.recipeapp.models.Users

interface RegisterRepo {

    suspend fun insertUser(user: Users)
    suspend fun isEmailExist(email: String): Boolean

}