package com.example.recipeapp.Aucthentication.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModelFactory(private val userRepo: UserRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            RegisterViewModel(userRepo) as T
        }else{
            throw IllegalArgumentException("RegisterViewModel not found")
        }
    }
}