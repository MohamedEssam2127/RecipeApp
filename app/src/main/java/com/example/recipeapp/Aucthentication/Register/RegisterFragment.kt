package com.example.recipeapp.Aucthentication.Register

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
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepoImp
import com.example.recipeapp.Aucthentication.Register.RegisterViewModel.RegisterViewModel
import com.example.recipeapp.Aucthentication.AuthViewModelFactory.ViewModelFactory
import com.example.recipeapp.Aucthentication.ViewValidations.validations
import com.example.recipeapp.R
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.google.android.material.textfield.TextInputEditText

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
            constructor = { userRepo -> RegisterViewModel(userRepo) },
            UserRepoImp(
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

        register_btn.setOnClickListener {
            handleRegistration(firstName,lastName,email_editText, password_editText)
        }

        login_btn.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        viewModel.userExistStatus.observe(viewLifecycleOwner, Observer { exists ->

            if (exists) {
                Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
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

    private fun handleRegistration(firstName: TextInputEditText, lastName: TextInputEditText,emailEdittext: TextInputEditText?, passwordEdittext: TextInputEditText?,) {

        val email = emailEdittext?.text.toString().trim()
        val password = passwordEdittext?.text.toString().trim()
        val firstName = firstName.text.toString().trim()
        val lastName = lastName.text.toString().trim()

        validations.validateEmail(email)
        validations.validatePassword(password)
        validations.validateFirstName(firstName)
        validations.validateLastName(lastName)

        val isEmailValid = validations.isEmailValid.value ?: false
        val isPasswordValid = validations.isPasswordValid.value ?: false
        val isFirstNameValid = validations.isFirstNameValid.value ?: false
        val isLastNameValid = validations.isLastNameValid.value ?: false

        if (isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid) {
            viewModel.checkIfUserExists(email)
        } else {
            when {
                !isEmailValid -> Toast.makeText(
                    context,
                    "Invalid Email Address",
                    Toast.LENGTH_SHORT
                ).show()

                !isPasswordValid -> Toast.makeText(
                    context,
                    "Enter Strong Password",
                    Toast.LENGTH_SHORT
                ).show()
                !isFirstNameValid -> Toast.makeText(
                    context,
                    "First Name is Required",
                    Toast.LENGTH_SHORT
                ).show()
                !isLastNameValid -> Toast.makeText(
                    context,
                    "Second Name is Required",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}