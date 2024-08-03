package com.example.recipeapp.database.LocalDataBase

import android.content.Context
import com.example.recipeapp.database.DAO.FavoriteMealDao
import com.example.recipeapp.database.UserDataBase
import com.example.recipeapp.database.DAO.UsersDao
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

class LocalDataBaseImp (context: Context) : LocalDataBase {


    private  var usersDao: UsersDao
    private  var favoriteMealDao: FavoriteMealDao

    init {
        val db = UserDataBase.getInstance(context)
        usersDao = db.productDao()
        favoriteMealDao = db.favoriteMealDao()
    }

    override suspend fun insertUser(user: Users) {
       usersDao.insertUser(user)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Users {
       return usersDao.getUserByEmailAndPassword(email, password)

    }

    override suspend fun isEmailExist(email: String): Boolean {
        return usersDao.isEmailExist(email)
    }
/////////////////////////////////////////////////////////////////////
                             // FAV //
    override suspend fun insertFavoriteMeal(meal: FavoriteMeal) {
    favoriteMealDao.insertFavoriteMeal(meal)
    }

    override suspend fun getUserWithFavorite(userId: Int): List<UserWithFavorite> {
     return favoriteMealDao.getUserWithFavorite(userId)
    }

    override suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        favoriteMealDao.deleteFavoriteMeal(favoriteMeal)
    }



    override   suspend fun isMealFavorite(id:String,uId:Int):Boolean{
        return favoriteMealDao.isMealFavorite(id,uId)
    }

    override suspend fun getFavoriteMealsByUserIdAndIdMeal(idMeal: Int, uId: Int): FavoriteMeal {
       return favoriteMealDao.getFavoriteMealsByUserIdAndIdMeal(idMeal,uId)
    }

}