package com.example.recipeapp.database.LocalDataBase

import android.content.Context
import com.example.recipeapp.database.FavoriteMealDao
import com.example.recipeapp.database.UserDataBase
import com.example.recipeapp.database.UsersDao
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

class LocalDataBaseImp (context: Context) : LocalDataBase {


    private  var usersDao: UsersDao
    private lateinit var favoriteMealDao: FavoriteMealDao

    init {
        val db = UserDataBase.getInstance(context)
        usersDao = db.productDao()
        favoriteMealDao = db.favoriteMealDao()
    }
    override suspend fun insertUser(user: Users) {
       usersDao.insertUser(user)
    }

    override suspend fun gellAllUsers(): List<Users> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Users {
       return usersDao.getUserByEmailAndPassword(email, password)

    }

    override suspend fun isUserExist(email: String, password: String): Boolean {
        return usersDao.isUserExist(email , password)
    }
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal) {
        favoriteMealDao.insertUser(favoriteMeal)
    }
    suspend fun deleteUser(user: Users){
        usersDao.deleteUser(user)
    }

    override suspend fun isEmailExist(email: String): Boolean {
        return usersDao.isEmailExist(email)
    }
}