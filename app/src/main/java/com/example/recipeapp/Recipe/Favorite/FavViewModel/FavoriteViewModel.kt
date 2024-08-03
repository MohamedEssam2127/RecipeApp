package com.example.recipeapp.Recipe.Favorite.FavViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepo
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.FavoriteMeal
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repo: FavoriteRepo) : ViewModel() {

    private val _FavoriteMeal = MutableLiveData<List<UserWithFavorite>>()
    val FavoriteMeal: LiveData<List<UserWithFavorite>> get() = _FavoriteMeal

    private val _FavoritelistAdapter = MutableLiveData<List<FavoriteMeal>>()
    val FavoritelistAdapter: LiveData<List<FavoriteMeal>> get() = _FavoritelistAdapter

//    private val _isFav= MutableLiveData<Boolean>()
//    val isFav: LiveData<Boolean> get() = _isFav

    fun insertFavoriteMeal (meal: FavoriteMeal){
        viewModelScope.launch {
            if( isMealFavorite(meal?.strMeal?:"",meal.userId)  == false){
                repo.insertFavoriteMeal(meal)
            }

        }
    }

    fun getFav(userId: Int) {
        viewModelScope.launch {
            val favoriteMeals = repo.getUserWithFavorite(userId)
            _FavoriteMeal.value = favoriteMeals
            if (favoriteMeals.isNotEmpty()){
                _FavoritelistAdapter.value = favoriteMeals[0].favoriteMeals
                Log.d("FavoriteViewModel", "Favorite meals size: ${FavoritelistAdapter.value}")
            }

        }
    }

    fun deleteFromFavList (favoriteMeal: FavoriteMeal){
        viewModelScope.launch {
           val favItem = getFavoriteMealsByUserIdAndIdMeal(favoriteMeal?.idMeal?.toInt() ?:-1,favoriteMeal.userId)
            favoriteMeal.id = favItem.id
            repo.deleteFavoriteMeal(favoriteMeal)
        }

    }


   private suspend fun getFavoriteMealsByUserIdAndIdMeal(idMeal: Int, userId: Int): FavoriteMeal {
        return repo.getFavoriteMealsByUserIdAndIdMeal(idMeal,userId)
    }

   suspend fun isMealFavorite(id:String,uId:Int) : Boolean{

            return repo.isMealFavorite(id,uId)


    }

//     fun isFavorite(id:String,uId:Int) : LiveData<Boolean> {
//
//         viewModelScope.launch (Dispatchers.Main){
//           val isfav =  repo.isMealFavorite(id,uId)
//             _isFav.value = isfav
//         }
//         return isFav
//
//
//    }

}