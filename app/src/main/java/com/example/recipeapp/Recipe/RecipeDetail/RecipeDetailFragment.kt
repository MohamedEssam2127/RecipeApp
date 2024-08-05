package com.example.recipeapp.Recipe.RecipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModel
import com.example.recipeapp.Recipe.Favorite.FavViewModel.FavoriteViewModelFactory
import com.example.recipeapp.Recipe.Favorite.Repo.FavoriteRepoImp
import com.example.recipeapp.Recipe.Home.view.HomeFragment.Companion.userId
import com.example.recipeapp.Recipe.RecipeActivity
import com.example.recipeapp.database.LocalDataBase.LocalDataBaseImp
import com.example.recipeapp.models.FavoriteMeal
import com.example.recipeapp.models.Meal
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipeDetailFragment : Fragment() {

    private val args: RecipeDetailFragmentArgs by navArgs()
    val recipe: Meal by lazy {
        args.recipeSent
    }
    private var titleView: TextView? = null
    private var categoryView: TextView? = null
    private var areaView: TextView? = null
    private var detailsView: TextView? = null
    private lateinit var favViewModel: FavoriteViewModel
    private val favIcon = view?.findViewById<ImageView>(R.id.Details_favBtn)
    private var isFavorite = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView = view.findViewById(R.id.detailRecipeTitle)
        categoryView = view.findViewById(R.id.detailCategoryBody)
        areaView = view.findViewById(R.id.detailAreaBody)
        detailsView = view.findViewById(R.id.detailDetailsBody)

        titleView?.text = recipe.strMeal
        categoryView?.text = recipe.strCategory
        areaView?.text = recipe.strArea
        detailsView?.text = recipe.strInstructions

        val recyclerView = view.findViewById<RecyclerView>(R.id.detailImagesRecyclerView)
        val adapter = RecipeDetailImagesAdapter(recipe.strMealThumb.split(","))
        val favIcon = view.findViewById<ImageView>(R.id.Details_favBtn)
        gettingViewModelReady()

        CoroutineScope(Dispatchers.IO).launch {

            isFavorite = favViewModel.isMealFavorite(recipe.strMeal, userId)
            withContext(Dispatchers.Main) {
                favIcon?.setImageResource(R.drawable.avorite)
                if (isFavorite) {
                    favIcon?.setImageResource(R.drawable.baseline_favorite_24)
                }
            }

        }
        val favoriteMeal = FavoriteMeal(
            idMeal = recipe.idMeal.toInt(),
            strCategory = recipe.strCategory,
            strMeal = recipe.strMeal,
            strMealThumb = recipe.strMealThumb,
            strTags = recipe.strTags,
            strYoutube = recipe.strYoutube,
            userId = userId,
            strArea = recipe.strArea,
            strInstructions = recipe.strInstructions
        )
        favIcon.setOnClickListener {
            if (favoriteMeal != null) {
                if (!isFavorite) {
                    favViewModel.insertFavoriteMeal(favoriteMeal)
                    favIcon.setImageResource(R.drawable.baseline_favorite_24)
                    isFavorite = true
                } else {
                    (requireActivity() as RecipeActivity).showRemoveFavDialog(favoriteMeal) {
                        favViewModel.deleteFromFavList(favoriteMeal)
                        favIcon.setImageResource(R.drawable.avorite)
                        isFavorite = false
                    }
                }
                }
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

            val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtubeVideoView)
            viewLifecycleOwner.lifecycle.addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = recipe.strYoutube.substringAfter("v=")
                    youTubePlayer.cueVideo(videoId, 0f)
                    youTubePlayer.mute()
                }
            })

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