package com.example.recipeapp.Recipe.Favorite.Repo

import com.example.recipeapp.database.LocalDataBase.LocalDataBase
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal

class FavoriteRepoImp (private val localDataBase: LocalDataBase): FavoriteRepo {

    override suspend fun insertFavoriteMeal(meal: FavoriteMeal) {
        localDataBase.insertFavoriteMeal(meal)
    }

    override suspend fun getUserWithFavorite(userId: Int): List<UserWithFavorite> {
        return localDataBase.getUserWithFavorite(userId)
    }

    override suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        localDataBase.deleteFavoriteMeal(favoriteMeal)
    }



    override  suspend fun isMealFavorite(id:String,uId:Int):Boolean {
        return localDataBase.isMealFavorite(id,uId)
    }

    override suspend fun getFavoriteMealsByUserIdAndIdMeal(idMeal: Int, uId: Int): FavoriteMeal {
        return localDataBase.getFavoriteMealsByUserIdAndIdMeal(idMeal, uId)
    }


}