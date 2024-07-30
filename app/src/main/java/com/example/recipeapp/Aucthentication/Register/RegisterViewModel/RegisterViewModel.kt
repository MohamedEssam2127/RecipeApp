package com.example.recipeapp.Aucthentication.Register.RegisterViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepo
import com.example.recipeapp.models.Users
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo: UserRepo) : ViewModel() {

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

                _userExistStatus.postValue(false)
            }
        }
    }
}
