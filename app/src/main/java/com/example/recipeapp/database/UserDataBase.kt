package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.database.DAO.FavoriteMealDao
import com.example.recipeapp.database.DAO.UsersDao
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Users

@Database(entities = [Users::class,FavoriteMeal::class], version =1)
 abstract class UserDataBase : RoomDatabase() {

    abstract fun productDao(): UsersDao
    abstract fun favoriteMealDao(): FavoriteMealDao

    companion object {
        @Volatile
        private var instance: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            if (instance == null) {
                synchronized(lock = this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            UserDataBase::class.java,
                            name = "User_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                            .also { createdInstance ->
                                instance = createdInstance
                            }
                    }
                }
            }
            return instance!!
        }
    }
}