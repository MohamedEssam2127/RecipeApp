package com.example.recipeapp.Aucthentication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.recipeapp.R
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {
    lateinit var navController :androidx.navigation.NavController
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val loginViewModelFactory = LoginViewModelFactory()

        viewModel = ViewModelProvider(this,loginViewModelFactory).get(LoginViewModel::class.java)

        val email_editText = view.findViewById<TextInputEditText>(R.id.login_emailInputText)
        val password_editText = view.findViewById<TextInputEditText>(R.id.login_passwordInputText)
        val register_btn = view.findViewById<Button>(R.id.login_signupBtn)
        val login_btn = view.findViewById<Button>(R.id.login_loginBtn)

        login_btn.setOnClickListener {
            handleLogin(email_editText, password_editText)
        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun handleLogin(emailText: TextInputEditText?, passwordText: TextInputEditText?) {
        val email = emailText?.text.toString().trim()
        val password = passwordText?.text.toString().trim()
        viewModel.validateEmail(email)
        viewModel.validatePassword(password)

        val isEmailValid = viewModel.isEmailValid.value ?: false
        val isPasswordValid = viewModel.isPasswordValid.value ?: false

        if(isEmailValid && isPasswordValid) {

            navController.navigate(R.id.action_loginFragment_to_recipeActivity)
            requireActivity().finish()
        }
        else{
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
            }
        }
    }

}