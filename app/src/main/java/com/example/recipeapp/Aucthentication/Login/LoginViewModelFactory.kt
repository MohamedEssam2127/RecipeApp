package com.example.recipeapp.Aucthentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            //LoginViewModel(repositery) as T
            LoginViewModel() as T

        } else {
            throw IllegalArgumentException("User View Not Found")
        }
    }
}