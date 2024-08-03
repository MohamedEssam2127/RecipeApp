package com.example.recipeapp.Aucthentication.Login.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Aucthentication.Login.Repository.LoginRepo
import com.example.recipeapp.models.Users
import kotlinx.coroutines.launch


class LoginViewModel(private val loginRepo: LoginRepo) :ViewModel() {

    private val _user = MutableLiveData<Users?>()
    val user: LiveData<Users?> get() = _user

    fun loginUser(email: String, password: String) {

        viewModelScope.launch {

            try {

                val user = loginRepo.getUserByEmailAndPassword(email, password)
                _user.postValue(user)

            } catch (e: Exception) {

                _user.postValue(null)
            }
        }
    }

}