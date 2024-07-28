package com.example.recipeapp.Aucthentication.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R

class RegisterFragment : Fragment() {

    lateinit var navController :androidx.navigation.NavController

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
        val register_btn = view.findViewById<Button>(R.id.register_signupBtn)
        val login_btn = view.findViewById<Button>(R.id.register_loginBtn)

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_recipeActivity)
            requireActivity().finish()
        }

        login_btn.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

}