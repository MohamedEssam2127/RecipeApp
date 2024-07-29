package com.example.recipeapp.Recipe.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getRecipesByLetter()
        viewModel.recipes.observe(viewLifecycleOwner){recipeResponce ->
            val adapter = listRecipeAdapter(recipeResponce)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_popular_recipe)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



}