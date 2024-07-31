package com.example.recipeapp.Recipe.Home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModelFactory
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepoImp
import com.example.recipeapp.Recipe.Home.HomeViewModel.HomeViewModel
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.example.recipeapp.models.FavoriteMeal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favoriteMeal: FavoriteMeal
    private var strMealRandom: String = ""
    var isFavorite  =false
    companion object{
        private lateinit var sharedPreferences: SharedPreferences
        var userId :Int =-1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gettingViewModelReady()
        sharedPreferences=requireActivity().getSharedPreferences("user_id",0)
         userId = sharedPreferences.getInt("user_id", -1)
        viewModel.getRecipesByLetter()
        viewModel.recipes.observe(viewLifecycleOwner) { recipeResponce ->
            val adapter = listRecipeAdapter(recipeResponce,favViewModel)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_popular_recipe)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter.onItemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(it)
                findNavController().navigate(action)
            }
        }

        viewModel.getRandomMeal()

        viewModel.randomMeal.observe(viewLifecycleOwner) { recipeResponce ->
            if(recipeResponce.meals.isNotEmpty()){
                favoriteMeal = FavoriteMeal(
                    recipeResponce.meals[0].idMeal.toInt(),
                    recipeResponce.meals[0].strCategory,
                    recipeResponce.meals[0].strMeal,
                    recipeResponce.meals[0].strMealThumb,
                    recipeResponce.meals[0].strTags,
                    recipeResponce.meals[0].strYoutube,
                    userId,
                    recipeResponce.meals[0].strArea,
                    recipeResponce.meals[0].strInstructions
                )


                val FavImg = view?.findViewById<ImageView>(R.id.Home_RandamImg_addfav)

                CoroutineScope(Dispatchers.Main).launch {
                    FavImg?.setImageResource(R.drawable.avorite)
                    isFavorite = favViewModel.isMealFavorite(recipeResponce.meals[0].strMeal, userId)
                    if (isFavorite) {
                        FavImg?.setImageResource(R.drawable.baseline_favorite_24)
                    }
                }

                val image = view?.findViewById<ImageView>(R.id.random_image)
                val title = view?.findViewById<TextView>(R.id.titletext)
                title?.text = recipeResponce.meals[0].strMeal
                strMealRandom = recipeResponce.meals[0].strMeal
                if (image != null) {
                    Glide.with(this).load(recipeResponce.meals[0].strMealThumb).apply(
                        RequestOptions().placeholder(R.drawable.baseline_access_time_24)
                            .error(R.drawable.baseline_assignment_late_24)
                    ).into(image)
                }
                image?.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(recipeResponce.meals[0])
                    findNavController().navigate(action)
                }
            }

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val FavImg = view.findViewById<ImageView>(R.id.Home_RandamImg_addfav)
        FavImg.setOnClickListener {

            if(favoriteMeal != null){
                if (!isFavorite) {
                   favViewModel.insertFavoriteMeal(favoriteMeal)
                    FavImg.setImageResource(R.drawable.baseline_favorite_24)
                    Log.d("SAD", " is added random to fav")
                    isFavorite = true
                }else{
                   favViewModel.deleteFromFavList(favoriteMeal)
                    FavImg.setImageResource(R.drawable.avorite)
                    Log.d("SAD", " is already   random  fav")
                    isFavorite = false
                }
            }
        }
    }

    private fun gettingViewModelReady() {
        val productViewModelFactory = FavoriteViewModelFactory(
            FavoriteRepoImp(
                LocalDataBaseImp(requireContext())
            )
        )
        favViewModel =
            ViewModelProvider(this, productViewModelFactory).get(FavoriteViewModel::class.java)
    }

}