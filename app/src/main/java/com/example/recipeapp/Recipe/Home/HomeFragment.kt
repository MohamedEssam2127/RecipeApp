package com.example.recipeapp.Recipe.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Home.HomeViewModel.HomeViewModel

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

        viewModel.getRandomMeal()
        viewModel.randomMeal.observe(viewLifecycleOwner){recipeResponce ->
            val image = view?.findViewById<ImageView>(R.id.random_image)
            val title =  view?.findViewById<TextView>(R.id.titletext)
            title?.text = recipeResponce.meals[0].strMeal

            if (image != null) {
                Glide.with(this).load(recipeResponce.meals[0].strMealThumb).apply(
                    RequestOptions().placeholder(R.drawable.baseline_access_time_24)
                        .error(R.drawable.baseline_assignment_late_24)
                ).into(image)
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



}