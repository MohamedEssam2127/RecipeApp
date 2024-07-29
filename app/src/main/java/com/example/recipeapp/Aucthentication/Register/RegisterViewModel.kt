package com.example.recipeapp.Aucthentication.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isFirstNameValid = MutableLiveData<Boolean>()
    val isFirstNameValid: LiveData<Boolean> get() = _isFirstNameValid

    private val _isLastNameValid = MutableLiveData<Boolean>()
    val isLastNameValid: LiveData<Boolean> get() = _isLastNameValid

    fun validateEmail(email: String) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        _isEmailValid.value = email.matches(emailPattern.toRegex())
    }

    fun validatePassword(password: String) {
        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}"
        _isPasswordValid.value = password.matches(passwordPattern.toRegex())
    }

    fun validateFirstName(firstName: String) {
        _isFirstNameValid.value = firstName.isNotEmpty()
    }

    fun validateLastName(lastName: String) {
        _isLastNameValid.value = lastName.isNotEmpty()
    }
}
