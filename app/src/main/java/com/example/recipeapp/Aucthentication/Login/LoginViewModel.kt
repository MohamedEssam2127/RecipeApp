package com.example.recipeapp.Aucthentication.Login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText


class LoginViewModel:ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    fun validateEmail(email: String) {
        // at least one character before @, one character before ., and one character after .
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        _isEmailValid.value = email.matches(emailPattern.toRegex())
    }

    fun validatePassword(password: String) {
        // at least one upper case, one lower case, one digit, and 8 characters
        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}"
        _isPasswordValid.value = password.matches(passwordPattern.toRegex())
    }






}