package com.example.recipeapp.Aucthentication.Login.Repository

import com.example.recipeapp.models.Users

interface LoginRepo {

    suspend fun getUserByEmailAndPassword(email: String, password: String): Users

}