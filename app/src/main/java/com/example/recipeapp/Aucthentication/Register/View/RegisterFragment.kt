package com.example.recipeapp.Aucthentication.Register.View

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Aucthentication.Register.ViewModel.RegisterViewModel
import com.example.recipeapp.Aucthentication.AuthViewModelFactory.ViewModelFactory
import com.example.recipeapp.Aucthentication.Register.Repository.RegisterRepoImp
import com.example.recipeapp.Aucthentication.ViewValidations.validations
import com.example.recipeapp.R
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var navController :androidx.navigation.NavController
    private lateinit var viewModel: RegisterViewModel
    val validations : validations = validations()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val userViewModelFactory = ViewModelFactory(
            RegisterViewModel::class.java,
            constructorRegister = { registerRepo -> RegisterViewModel(registerRepo) },
            registerRepo = RegisterRepoImp(
                localDataBase = LocalDataBaseImp(requireContext())
            )
        )

        sharedPreferences=requireActivity().getSharedPreferences("user",0)
        navController = findNavController()

        viewModel = ViewModelProvider(this,userViewModelFactory).get(RegisterViewModel::class.java)

        val register_btn = view.findViewById<Button>(R.id.register_signupBtn)
        val login_btn = view.findViewById<Button>(R.id.register_loginBtn)
        val firstName = view.findViewById<TextInputEditText>(R.id.register_nameText)
        val lastName = view.findViewById<TextInputEditText>(R.id.register_SecName_TextInput)
        val email_editText = view.findViewById<TextInputEditText>(R.id.register_email_TextInput)
        val password_editText = view.findViewById<TextInputEditText>(R.id.register_password_TextInput)
        val confirmPassword_editText = view.findViewById<TextInputEditText>(R.id.register_confirmPassword_TextInput)


        register_btn.setOnClickListener {
            handleRegistration(firstName,lastName,email_editText, password_editText,confirmPassword_editText)
        }

        login_btn.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        viewModel.userExistStatus.observe(viewLifecycleOwner, Observer { exists ->

            if (exists) {

                val emailhelper = view?.findViewById<TextInputLayout>(R.id.register_email_InputLayout)
                if (emailhelper != null)
                    emailhelper.helperText = "*User already exists"

            } else {
                viewModel.registerUser(
                    firstName.text.toString().trim(),
                    lastName.text.toString().trim(),
                    email_editText.text.toString().trim(),
                    password_editText.text.toString().trim()
                )
                navController.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })

        viewModel.user.observe(viewLifecycleOwner,Observer { user ->
            if (user != null) {
                // Save the user ID in SharedPreferences
                sharedPreferences.edit().putInt("user_id", user.userId).apply()
            }
        })
    }

    private fun handleRegistration(firstName: TextInputEditText, lastName: TextInputEditText,emailEdittext: TextInputEditText?, passwordEdittext: TextInputEditText?,confirmPasswordEditText: TextInputEditText?) {

        val email = emailEdittext?.text.toString().trim()
        val password = passwordEdittext?.text.toString().trim()
        val firstName = firstName.text.toString().trim()
        val lastName = lastName.text.toString().trim()
        val confirmPassword = confirmPasswordEditText?.text.toString().trim()

        validations.validateEmail(email)
        validations.validatePassword(password)
        validations.validateFirstName(firstName)
        validations.validateLastName(lastName)
        validations.confirmPassword(password,confirmPassword)


        if (passwordEdittext != null) {
            val helper= view?.findViewById<TextInputLayout>(R.id.register_password_InputLayout)

            if (passwordEdittext.length() < 8) {
                if (helper != null) {
                    helper.helperText = "*Password must be at least 8 characters"
                }
            }

            else if (passwordEdittext.length() > 16) {
                if (helper != null) {
                    helper.helperText = "*Password must be at most 16 characters"
                }
            }

            else{
                if (helper != null) {
                    helper.helperText = ""
                }
            }
        }


        val isEmailValid = validations.isEmailValid.value ?: false
        val isPasswordValid = validations.isPasswordValid.value ?: false
        val isFirstNameValid = validations.isFirstNameValid.value ?: false
        val isLastNameValid = validations.isLastNameValid.value ?: false
        val isConfirmPasswordValid = validations.isConfirmPasswordValid.value ?: false


        if (isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
            viewModel.checkIfUserExists(email)
        } else {
            when {
                !isEmailValid -> {
                    val emailhelper = view?.findViewById<TextInputLayout>(R.id.register_email_InputLayout)
                    if (emailhelper != null)
                        emailhelper.helperText = "*Email Address not found"
                }

                !isPasswordValid -> {
                    val passwordhelper = view?.findViewById<TextInputLayout>(R.id.register_password_InputLayout)
                    if (passwordhelper != null)
                        passwordhelper.helperText = "*invalid Password Format"
                }

                !isFirstNameValid -> {
                    val firstNameHelper = view?.findViewById<TextInputLayout>(R.id.register_Fname_InputLayout)
                    if (firstNameHelper != null)
                        firstNameHelper.helperText = "*First Name is Required"
                }
                !isLastNameValid -> {
                    val lastNameHelper = view?.findViewById<TextInputLayout>(R.id.register_secName_InputLayout)
                    if (lastNameHelper != null)
                        lastNameHelper.helperText = "*Last Name is Required"
                }
                !isConfirmPasswordValid -> {
                    val confirmPasswordHelper = view?.findViewById<TextInputLayout>(R.id.register_confirmPassword_InputLayout)
                    if (confirmPasswordHelper != null)
                        confirmPasswordHelper.helperText = "*Password does not match"
                }
            }

        }
    }
}