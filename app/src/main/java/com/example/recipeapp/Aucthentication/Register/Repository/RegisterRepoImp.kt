package com.example.recipeapp.Aucthentication.Register.Repository

import com.example.recipeapp.database.LocalDataBase.LocalDataBase
import com.example.recipeapp.models.Users

class RegisterRepoImp(private val localDataBase: LocalDataBase): RegisterRepo {

    override suspend fun insertUser( user: Users) {
        localDataBase.insertUser(user)
    }

    override suspend fun isEmailExist(email: String): Boolean {
        return localDataBase.isEmailExist(email)
    }
}