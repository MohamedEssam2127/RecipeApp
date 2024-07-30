package com.example.recipeapp.Recipe.RecipeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal


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

//        val webView = view.findViewById<WebView>(R.id.youtubeVideoView)
//        webView.loadUrl(recipe.strYoutube)




    }

}