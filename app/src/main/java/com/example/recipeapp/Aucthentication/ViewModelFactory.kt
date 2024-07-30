package com.example.recipeapp.Aucthentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo

class ViewModelFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val constructor: (UserRepo) -> T,
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
