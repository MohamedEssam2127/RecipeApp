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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModelFactory
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepoImp
import com.example.recipeapp.Recipe.Home.HomeViewModel.FactoryViewModelHome
import com.example.recipeapp.Recipe.Home.HomeViewModel.HomeViewModel
import com.example.recipeapp.Recipe.RecipeActivity
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.network.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favoriteMeal: FavoriteMeal
    private var strMealRandom: String = ""
    private var isFavorite = false

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        var userId: Int = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gettingFavoriteViewModelReady()
        gettingHomeViewModelReady()
        sharedPreferences = requireActivity().getSharedPreferences("user_id", 0)
        userId = sharedPreferences.getInt("user_id", -1)

        viewModel.getRecipesByLetter()
        viewModel.recipes.observe(viewLifecycleOwner) { recipeResponce ->
            val adapter = listRecipeAdapter(recipeResponce, favViewModel)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_popular_recipe)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter.onItemClick = {     // get the item was clicked on
                lifecycleScope.launch { // get the complete meal object from the Api
                    val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(viewModel.getMealById(it.idMeal))
                    findNavController().navigate(action)
                }
            }
        }

        viewModel.getAllMealCategories()
        viewModel.categories.observe(viewLifecycleOwner) {
            val adapter = CategoryListAdapetr(viewModel.categories.value!!)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.categoriesRV)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter.onItemClick = {
                viewModel.getRecipeByCategory(it.strCategory)
            }
        }

        viewModel.getRandomMeal()

        viewModel.randomMeal.observe(viewLifecycleOwner) { recipeResponce ->
            if(recipeResponce.meals.isNotEmpty()){
                favoriteMeal = FavoriteMeal(
                    idMeal =recipeResponce.meals[0].idMeal.toInt(),
                    strCategory =recipeResponce.meals[0].strCategory,
                    strMeal=  recipeResponce.meals[0].strMeal,
                    strMealThumb= recipeResponce.meals[0].strMealThumb,
                    strTags =recipeResponce.meals[0].strTags,
                    strYoutube =recipeResponce.meals[0].strYoutube,
                    userId= userId,
                    strArea =recipeResponce.meals[0].strArea,
                    strInstructions =recipeResponce.meals[0].strInstructions
                )


                val FavImg = view?.findViewById<ImageView>(R.id.Home_RandamImg_addfav)

                CoroutineScope(Dispatchers.IO).launch {

                    isFavorite = favViewModel.isMealFavorite(recipeResponce.meals[0].strMeal, userId)
                    withContext(Dispatchers.Main) {
                        FavImg?.setImageResource(R.drawable.avorite)
                        if (isFavorite) {
                            FavImg?.setImageResource(R.drawable.baseline_favorite_24)
                        }
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
                    lifecycleScope.launch {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(
                                viewModel.getMealById(recipeResponce.meals[0].idMeal)
                            )
                        findNavController().navigate(action)
                    }
                }
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as RecipeActivity).showExitDialog()
            }
        })

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


    private fun gettingHomeViewModelReady() {
        val HomeViewModelFactory = FactoryViewModelHome(
                RecipeRepository()
                )
                viewModel =
                ViewModelProvider(this, HomeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun gettingFavoriteViewModelReady() {
        val productViewModelFactory = FavoriteViewModelFactory(
            FavoriteRepoImp(
                LocalDataBaseImp(requireContext())
            )
        )
        favViewModel =
            ViewModelProvider(this, productViewModelFactory).get(FavoriteViewModel::class.java)
    }




}