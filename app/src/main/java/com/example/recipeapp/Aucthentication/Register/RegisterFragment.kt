package com.example.recipeapp.Aucthentication.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : Fragment() {

    lateinit var navController :androidx.navigation.NavController
    private lateinit var viewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

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


    }

    private fun handleRegistration(firstName: TextInputEditText, lastName: TextInputEditText,emailEdittext: TextInputEditText?, passwordEdittext: TextInputEditText?,) {
        val email = emailEdittext?.text.toString().trim()
        val password = passwordEdittext?.text.toString().trim()
        val firstName = firstName.text.toString().trim()
        val lastName = lastName.text.toString().trim()

        viewModel.validateEmail(email)
        viewModel.validatePassword(password)
        viewModel.validateFirstName(firstName)
        viewModel.validateLastName(lastName)

        val isEmailValid = viewModel.isEmailValid.value ?: false
        val isPasswordValid = viewModel.isPasswordValid.value ?: false
        val isFirstNameValid = viewModel.isFirstNameValid.value ?: false
        val isLastNameValid = viewModel.isLastNameValid.value ?: false

        if (isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid) {

            navController.navigate(R.id.action_registerFragment_to_recipeActivity)
            requireActivity().finish()
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