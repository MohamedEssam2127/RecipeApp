package com.example.recipeapp.Recipe.RecipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class RecipeDetailFragment : Fragment() {

    private val args: RecipeDetailFragmentArgs by navArgs()
    val recipe: Meal by lazy {
        args.recipeSent
    }
    private var titleView: TextView? = null
    private var categoryView: TextView? = null
    private var areaView: TextView? = null
    private var detailsView: TextView? = null

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

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

}