package com.example.recipeapp.Recipe.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.models.Meal

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchView: SearchView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var notFoundImageView: ImageView
    private lateinit var adapter: RecipeAdapter

    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize views
        searchView = view.findViewById(R.id.search_view)
        recipeRecyclerView = view.findViewById(R.id.recipeRecyclerView)
        notFoundImageView = view.findViewById(R.id.not_found_image)

        // Set up RecyclerView
        recipeRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecipeAdapter { meal -> navigateToDetail(meal) }
        recipeRecyclerView.adapter = adapter

        // Set up SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do nothing on submit, as we are handling live search
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    Log.d(TAG, "Query text changed: $it")
                    searchViewModel.searchMealByName(it)
                }
                return true
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        // Observe LiveData from ViewModel
        searchViewModel.meals.observe(viewLifecycleOwner, Observer { meals ->
            Log.d(TAG, "Updating UI with meals: $meals")
            if (meals.isEmpty()) {
                notFoundImageView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
            } else {
                notFoundImageView.visibility = View.GONE
                recipeRecyclerView.visibility = View.VISIBLE
                adapter.updateData(meals)
            }
        })

        searchViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            Log.d(TAG, "Error message received: $error")
            notFoundImageView.visibility = View.VISIBLE
            recipeRecyclerView.visibility = View.GONE
        })
    }

    private fun navigateToDetail(meal: Meal) {
        Log.d("SearchFragment", "Navigating to details with meal: $meal")
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal)
        findNavController().navigate(action)
    }
}
