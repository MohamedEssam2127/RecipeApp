package com.example.recipeapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.models.Users
@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Users)

    @Query("SELECT * FROM Users")
    suspend fun gellAllUsers(): List<Users>

    @Query("SELECT * FROM Users WHERE Email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): Users

    @Query("SELECT EXISTS(SELECT * FROM Users WHERE email = :email AND password= :password)")
    suspend fun isUserExist(email: String, password: String ): Boolean




}