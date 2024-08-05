package com.example.recipeapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipeapp.database.UserWithFavorite
import com.example.recipeapp.models.Users
@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Users)

    @Query("SELECT * FROM Users WHERE Email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): Users

    @Query("SELECT EXISTS(SELECT * FROM Users WHERE email = :email)")
    suspend fun isEmailExist(email: String): Boolean


//    @Transaction
//    @Query("SELECT * FROM Users WHERE userId = :userId")
//    fun getUserWithTasks(userId: Int): LiveData<UserWithFavorite>


}