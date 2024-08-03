package com.example.recipeapp.Aucthentication.ViewValidations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class validations {

    val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

     val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    val _isFirstNameValid = MutableLiveData<Boolean>()
    val isFirstNameValid: LiveData<Boolean> get() = _isFirstNameValid

     val _isLastNameValid = MutableLiveData<Boolean>()
    val isLastNameValid: LiveData<Boolean> get() = _isLastNameValid

    val _isConfirmPasswordValid = MutableLiveData<Boolean>()
    val isConfirmPasswordValid: LiveData<Boolean> get() = _isConfirmPasswordValid


    fun validateEmail(email: String) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        _isEmailValid.value = email.matches(emailPattern.toRegex())
    }

    fun validatePassword(password: String) {
        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,16}"
        _isPasswordValid.value = password.matches(passwordPattern.toRegex())
    }

    fun validateFirstName(firstName: String) {
        _isFirstNameValid.value = firstName.isNotEmpty()
    }

    fun validateLastName(lastName: String) {
        _isLastNameValid.value = lastName.isNotEmpty()
    }

    fun confirmPassword(password: String, confirmPassword: String) {
        _isConfirmPasswordValid.value = password == confirmPassword

    }


}