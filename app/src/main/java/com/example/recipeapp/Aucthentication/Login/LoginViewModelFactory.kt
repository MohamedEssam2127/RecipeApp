package com.example.recipeapp.Aucthentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo

class LoginViewModelFactory(private val userRepo: UserRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(userRepo) as T

        } else {
            throw IllegalArgumentException("User View Not Found")
        }
    }
}