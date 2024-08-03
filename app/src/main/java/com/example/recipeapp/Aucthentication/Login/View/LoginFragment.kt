package com.example.recipeapp.Aucthentication.Login.View

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Aucthentication.Login.ViewModel.LoginViewModel
import com.example.recipeapp.Aucthentication.AuthViewModelFactory.ViewModelFactory
import com.example.recipeapp.Aucthentication.Login.Repository.LoginRepoImp
import com.example.recipeapp.Aucthentication.ViewValidations.validations
import com.example.recipeapp.R
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var navController :androidx.navigation.NavController
    private lateinit var viewModel: LoginViewModel
    val validations : validations = validations()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        sharedPreferences=requireActivity().getSharedPreferences("user_id",0)
        navController = findNavController()

        // Check if user is already logged in

        val userId = sharedPreferences.getInt("user_id", -1)
        if (userId != -1) {
            navController.navigate(R.id.action_loginFragment_to_recipeActivity)
            requireActivity().finish()
            return
        }

        val loginViewModelFactory = ViewModelFactory(
            LoginViewModel::class.java,
            constructorLogin = { loginRepo -> LoginViewModel(loginRepo) },
            LoginRepoImp(
                localDataBase = LocalDataBaseImp(requireContext())
            )
        )

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

        viewModel.user.observe(viewLifecycleOwner) { user ->

            // if Login Successful then save user id in shared preferences
            if (user != null) {

                sharedPreferences.edit().putInt("user_id", user.userId).apply()
                navController.navigate(R.id.action_loginFragment_to_recipeActivity)
                requireActivity().finish()

            } else {
                Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleLogin(emailText: TextInputEditText?, passwordText: TextInputEditText?) {

        val email = emailText?.text.toString().trim()
        val password = passwordText?.text.toString().trim()

        validations.validateEmail(email)
        validations.validatePassword(password)

        val isEmailValid = validations.isEmailValid.value ?: false
        val isPasswordValid = validations.isPasswordValid.value ?: false

        if(isEmailValid && isPasswordValid) {
            viewModel.loginUser(email, password)
        }
        else{
            when {
                !isEmailValid -> {
                    val emailhelper = view?.findViewById<TextInputLayout>(R.id.login_emailInputLayout)
                    if (emailhelper != null)
                        emailhelper.helperText = "*Email Address not found"
                }

                !isPasswordValid -> {
                    val passwordhelper = view?.findViewById<TextInputLayout>(R.id.login_passwordInputLayout)
                    if (passwordhelper != null)
                        passwordhelper.helperText = "*Wrong Password"
                }
            }
        }
    }
}