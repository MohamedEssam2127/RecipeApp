package com.example.recipeapp.Aucthentication.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo
import com.example.recipeapp.models.Users
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo: UserRepo) : ViewModel() {



//    private val _isEmailValid = MutableLiveData<Boolean>()
//    val isEmailValid: LiveData<Boolean> get() = _isEmailValid
//
//    private val _isPasswordValid = MutableLiveData<Boolean>()
//    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid
//
//    private val _isFirstNameValid = MutableLiveData<Boolean>()
//    val isFirstNameValid: LiveData<Boolean> get() = _isFirstNameValid
//
//    private val _isLastNameValid = MutableLiveData<Boolean>()
//    val isLastNameValid: LiveData<Boolean> get() = _isLastNameValid

//    fun validateEmail(email: String) {
//        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//        _isEmailValid.value = email.matches(emailPattern.toRegex())
//    }
//
//    fun validatePassword(password: String) {
//        val passwordPattern = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}"
//        _isPasswordValid.value = password.matches(passwordPattern.toRegex())
//    }
//
//    fun validateFirstName(firstName: String) {
//        _isFirstNameValid.value = firstName.isNotEmpty()
//    }
//
//    fun validateLastName(lastName: String) {
//        _isLastNameValid.value = lastName.isNotEmpty()
//    }

    private val _user = MutableLiveData<Users?>()
    val user: LiveData<Users?> get() = _user

    private val _userExistStatus = MutableLiveData<Boolean>()
    val userExistStatus: LiveData<Boolean> get() = _userExistStatus

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = Users(
                    userFirstName = firstName,
                    userSecondName = lastName,
                    userEmail = email,
                    userPassword = password
                )
                userRepo.insertUser(user)
                _user.postValue(user)

            }catch (e: Exception) {
                // Handle exceptions or errors here
                _user.postValue(null)
            }
        }
    }
    fun checkIfUserExists(email: String) {
        viewModelScope.launch {
            try {
                val exists = userRepo.isEmailExist(email)
                _userExistStatus.postValue(exists)
            } catch (e: Exception) {
                // Handle exceptions or errors here
                _userExistStatus.postValue(false)
            }
        }
    }
}
