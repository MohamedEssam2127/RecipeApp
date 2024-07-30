package com.example.recipeapp.Aucthentication.Login

import android.content.SharedPreferences
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Aucthentication.AuthRepository.UserRepoImp
import com.example.recipeapp.Aucthentication.Register.ViewModelFactory
import com.example.recipeapp.Aucthentication.validations

import com.example.recipeapp.R
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var navController :androidx.navigation.NavController
    private lateinit var viewModel: LoginViewModel
    val validations : validations = validations()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        sharedPreferences=requireActivity().getSharedPreferences("user_id",0)
        navController = findNavController()

        val loginViewModelFactory = ViewModelFactory(
            LoginViewModel::class.java,
            constructor = { userRepo -> LoginViewModel(userRepo) },
            UserRepoImp(
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

//        viewModel.userExistStatus.observe(viewLifecycleOwner) {
//
//            if(it) {
//                viewModel.loginUser(email_editText.text.toString(), password_editText.text.toString())
//                navController.navigate(R.id.action_loginFragment_to_recipeActivity)
//                requireActivity().finish()
//            }
//            else {
//                Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
//            }
//        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
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

//        viewModel.validateEmail(email)
//        viewModel.validatePassword(password)

        validations.validateEmail(email)
        validations.validatePassword(password)

//        val isEmailValid = viewModel.isEmailValid.value ?: false
//        val isPasswordValid = viewModel.isPasswordValid.value ?: false

        val isEmailValid = validations.isEmailValid.value ?: false
        val isPasswordValid = validations.isPasswordValid.value ?: false

        if(isEmailValid && isPasswordValid) {
            viewModel.loginUser(email, password)
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