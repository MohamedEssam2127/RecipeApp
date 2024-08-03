package com.example.recipeapp.Recipe.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

    // ViewModel for managing search logic
    private val searchViewModel: SearchViewModel by viewModels()

    // UI components
    private lateinit var searchView: SearchView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var placeholderTextView: TextView
    private lateinit var notFoundImageView: ImageView

    // Adapter for displaying search results
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
        placeholderTextView = view.findViewById(R.id.placeholder_text_view)
        notFoundImageView = view.findViewById(R.id.not_found_image)

        searchView.queryHint = "Search for a recipe"
        searchView.onActionViewExpanded()
        searchView.clearFocus()

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
                    if (it.isEmpty()) {
                        // Show placeholder if search query is empty
                        placeholderTextView.visibility = View.VISIBLE
                        recipeRecyclerView.visibility = View.GONE
                        notFoundImageView.visibility = View.GONE
                    } else {
                        // Hide placeholder and search for meals
                        placeholderTextView.visibility = View.GONE
                        searchViewModel.searchMealByName(it)
                    }
                }
                return true
            }
        })

        // Initial state: Display placeholderTextView if the search view is empty
        if (searchView.query.isEmpty()) {
            placeholderTextView.visibility = View.VISIBLE
            recipeRecyclerView.visibility = View.GONE
            notFoundImageView.visibility = View.GONE
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        // Observe LiveData from ViewModel
        searchViewModel.meals.observe(viewLifecycleOwner, Observer { meals ->
            Log.d(TAG, "Updating UI with meals: $meals")
            if (meals.isEmpty() && searchView.query.isNotEmpty()) {
                // Show "not found" image if no meals found for the query
                notFoundImageView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
            } else if (meals.isNotEmpty()) {
                // Show list of meals if found
                notFoundImageView.visibility = View.GONE
                recipeRecyclerView.visibility = View.VISIBLE
                adapter.updateData(meals)
            } else if (searchView.query.isEmpty()) {
                // Show placeholder if search query is empty
                placeholderTextView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
                notFoundImageView.visibility = View.GONE
            }
        })

        // Observe error messages from ViewModel
        searchViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            Log.d(TAG, "Error message received: $error")
            notFoundImageView.visibility = View.VISIBLE
            recipeRecyclerView.visibility = View.GONE
        })
    }

    // Navigate to the detail screen for the selected meal
    private fun navigateToDetail(meal: Meal) {
        Log.d("SearchFragment", "Navigating to details with meal: $meal")
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal)
        findNavController().navigate(action)
    }
}
