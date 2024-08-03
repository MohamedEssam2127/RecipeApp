package com.example.recipeapp.Aucthentication.Login.Repository

import com.example.recipeapp.models.Users
import com.example.recipeapp.database.LocalDataBase.LocalDataBase

class LoginRepoImp(private val localDataBase: LocalDataBase) : LoginRepo {

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Users {
        return localDataBase.getUserByEmailAndPassword(email, password)
    }
}