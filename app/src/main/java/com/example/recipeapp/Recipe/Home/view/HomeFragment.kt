package com.example.recipeapp.Recipe.Home.view

import NetworkLiveData
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
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
import com.example.recipeapp.Recipe.Home.Repo.RecipeRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: HomeViewModel
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favoriteMeal: FavoriteMeal
    private var strMealRandom: String = ""
    private var isFavorite = false
    private lateinit var networkLiveData: NetworkLiveData

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        var userId: Int = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkLiveData = NetworkLiveData(connectivityManager)
        sharedPreferences = requireActivity().getSharedPreferences("user_id", 0)
        userId = sharedPreferences.getInt("user_id", -1)
        networkLiveData.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {

                switchToNoInternetView(false)
                gettingFavoriteViewModelReady()
                gettingHomeViewModelReady()

                initializeRecipesList()     //prepare the Recipes RV
                initializeCategories()      //prepare the Categories and it's actions
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
                                val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(
                                        viewModel.getMealById(recipeResponce.meals[0].idMeal)
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }

                }


            } else {
                Toast.makeText(requireContext(), "Internet is lost", Toast.LENGTH_SHORT).show()
                switchToNoInternetView(true)
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
        toolbar = activity?.findViewById(R.id.toolbar) ?: return
        toolbar.visibility = View.VISIBLE
        super.onViewCreated(view, savedInstanceState)

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkLiveData = NetworkLiveData(connectivityManager)

        networkLiveData.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val FavImg = view.findViewById<ImageView>(R.id.Home_RandamImg_addfav)
                FavImg.setOnClickListener {

                    if(favoriteMeal != null){
                        if (!isFavorite) {
                            favViewModel.insertFavoriteMeal(favoriteMeal)
                            FavImg.setImageResource(R.drawable.baseline_favorite_24)
                            Log.d("SAD", " is added random to fav")
                            isFavorite = true
                        }else{
                            (requireActivity() as RecipeActivity).showRemoveFavDialog(favoriteMeal) {
                                favViewModel.deleteFromFavList(favoriteMeal)
                                FavImg.setImageResource(R.drawable.avorite)
                                Log.d("SAD", " is already   random  fav")
                                isFavorite = false
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Internet is lost", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun gettingHomeViewModelReady() {
        val HomeViewModelFactory = FactoryViewModelHome(
                RecipeRepositoryImpl()
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

    private fun initializeRecipesList(){
        viewModel.getRecipesByLetter()      // get a the Recipes for a random alphabet char
        viewModel.recipes.observe(viewLifecycleOwner) { recipeResponce ->
            val adapter = listRecipeAdapter(requireActivity() as RecipeActivity,recipeResponce,favViewModel)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_popular_recipe)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            adapter.onItemClick = {     // get the item was clicked on
                lifecycleScope.launch { // get the complete meal object from the Api
                    val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(
                            viewModel.getMealById(it.idMeal)
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initializeCategories(){
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

            val clearBtn = view?.findViewById<Button>(R.id.clearCategoryBtn)
            clearBtn?.setOnClickListener {
                viewModel.getRecipesByLetter()  // Restart the Recipes unselected categories
                adapter.clearSelectedCategory(resources)
            }
        }
    }

    fun switchToNoInternetView(isConnected: Boolean) {
        if (!isConnected) {
            view?.findViewById<View>(R.id.noInternetViewHome)?.visibility = View.GONE
            view?.findViewById<View>(R.id.fragmenthome)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<View>(R.id.noInternetViewHome)?.visibility = View.VISIBLE
        }
    }


}