package com.example.recipeapp.Recipe.Favorite

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModelFactory
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepoImp
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.example.recipeapp.models.FavoriteMeal

class FavoriteFragment : Fragment() {
    lateinit var viewModel: FavoriteViewModel

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences=requireActivity().getSharedPreferences("user_id",0)
        val userId = sharedPreferences.getInt("user_id", -1)

        val recyclerView = view.findViewById<RecyclerView>(R.id.favouriteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        gettingViewModelReady()
        viewModel.getFav(userId)

        viewModel.FavoriteMeal.observe(viewLifecycleOwner) { favoriteMeals ->

            val fav = favoriteMeals
            Log.d("FavoriteFragment", "Favorite meals size : ${fav.size}")
            if (fav.size==0){
                Log.d("FavoriteFragment", "Favorite meals: ${fav}")

            }else{
                val adapter = FavoriteAdapter(favoriteMeals[0].favoriteMeals)
                recyclerView.adapter = adapter
            }



        }
    }


    private fun gettingViewModelReady() {
        val productViewModelFactory = FavoriteViewModelFactory(
            FavoriteRepoImp(
                LocalDataBaseImp(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, productViewModelFactory).get(FavoriteViewModel::class.java)
    }


}