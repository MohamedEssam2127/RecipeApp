package com.example.recipeapp.Aucthentication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R

class LoginFragment : Fragment() {
    lateinit var navController :androidx.navigation.NavController

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
        val register_btn = view.findViewById<Button>(R.id.login_signupBtn)
        val login_btn = view.findViewById<Button>(R.id.login_loginBtn)

        login_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_recipeActivity)
            requireActivity().finish()

        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

}