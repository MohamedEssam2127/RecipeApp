package com.example.recipeapp.Aucthentication.AuthRepository

import com.example.recipeapp.database.LocalDataBase.LocalDataBase
import com.example.recipeapp.models.Users

class UserRepoImp(private val localDataBase: LocalDataBase): UserRepo {
    override suspend fun insertUser( user: Users) {
        localDataBase.insertUser(user)
    }



    override suspend fun isUserExist(email: String, password: String): Boolean {
        val user = localDataBase.getUserByEmailAndPassword(email, password)
        return user != null
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Users {
        return localDataBase.getUserByEmailAndPassword(email, password)
    }

    override suspend fun isEmailExist(email: String): Boolean {
        return localDataBase.isEmailExist(email)
    }

}