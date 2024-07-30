package com.example.recipeapp.Aucthentication.Register

import com.example.recipeapp.database.LocalDataBase.LocalDataBase
import com.example.recipeapp.models.Users

class UserRepoImp(private val localDataBase: LocalDataBase):UserRepo {
    override suspend fun insertUser( user: Users) {
        localDataBase.insertUser(user)
    }



    override suspend fun isUserExist(email: String, password: String): Boolean {
        val user = localDataBase.getUserByEmailAndPassword(email, password)
        return user != null
    }

}