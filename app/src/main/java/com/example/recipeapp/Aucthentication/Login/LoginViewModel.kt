package com.example.recipeapp.Aucthentication.Login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo
import com.example.recipeapp.models.Users
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepo: UserRepo) :ViewModel() {

//    private val _userExistStatus = MutableLiveData<Boolean>()
//    val userExistStatus: LiveData<Boolean> get() = _userExistStatus

    private val _user = MutableLiveData<Users?>()
    val user: LiveData<Users?> get() = _user

//    private val _isEmailValid = MutableLiveData<Boolean>()
//    val isEmailValid: LiveData<Boolean> get() = _isEmailValid
//
//    private val _isPasswordValid = MutableLiveData<Boolean>()
//    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid
//
//    fun validateEmail(email: String) {
//        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//        _isEmailValid.value = email.matches(emailPattern.toRegex())
//    }
//
//    fun validatePassword(password: String) {
//        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}"
//        _isPasswordValid.value = password.matches(passwordPattern.toRegex())
//    }

//    fun isUserExist(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val exists = userRepo.isUserExist(email, password)
//                _userExistStatus.postValue(exists)
//            } catch (e: Exception) {
//                // Handle exceptions or errors here
//                _userExistStatus.postValue(false)
//            }
//        }
//    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userRepo.getUserByEmailAndPassword(email, password)
                _user.postValue(user)
            } catch (e: Exception) {
                // Handle exceptions or errors here
                _user.postValue(null)
            }
        }
    }






}