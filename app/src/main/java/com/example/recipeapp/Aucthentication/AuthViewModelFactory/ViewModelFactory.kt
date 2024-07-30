package com.example.recipeapp.Aucthentication.AuthViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo

class ViewModelFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val constructor: (UserRepo) -> T,// this is a function that takes a UserRepo and returns a T which is a ViewModel
    private val userRepo: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(viewModelClass)) {
            constructor(userRepo) as T
        } else {
            throw IllegalArgumentException("${viewModelClass.simpleName} not found")
        }
    }
}
