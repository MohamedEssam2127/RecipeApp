package com.example.recipeapp.Aucthentication.AuthViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Aucthentication.Login.Repository.LoginRepo
import com.example.recipeapp.Aucthentication.Register.Repository.RegisterRepo

class ViewModelFactory<T : ViewModel>(

    private val viewModelClass: Class<T>,

    //  Login is optional
    private val constructorLogin: ((LoginRepo) -> T)? = null,
    private val loginRepo: LoginRepo? = null,

    //  Register is optional
    private val constructorRegister: ((RegisterRepo) -> T)? = null,
    private val registerRepo: RegisterRepo? = null

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(viewModelClass) -> {
                when {
                    loginRepo != null && constructorLogin != null -> constructorLogin?.let { it(loginRepo) } as T
                    registerRepo != null && constructorRegister != null -> constructorRegister?.let { it(registerRepo) } as T

                    else -> throw IllegalArgumentException("No appropriate constructor or repository provided for ${viewModelClass.simpleName}")
                }
            }
            else -> throw IllegalArgumentException("${viewModelClass.simpleName} not found")
        }
    }
}
