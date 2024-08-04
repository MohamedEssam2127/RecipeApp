package com.example.recipeapp.Recipe.Search.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.Recipe.Search.repo.SearchRepositoryImpl
import com.example.recipeapp.Recipe.Search.searchviewmodel.SearchViewModel
import com.example.recipeapp.Recipe.Search.searchviewmodel.SearchViewModelFactory
import com.example.recipeapp.models.Meal

/**
 * Fragment for handling meal searches and displaying search results.
 * Manages UI interactions, initializes the ViewModel, and sets up RecyclerView and SearchView.
 */
class SearchFragment : Fragment() {

    // ViewModel instance for managing search operations and data
    private lateinit var searchViewModel: SearchViewModel

    // UI components
    private lateinit var searchView: SearchView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var placeholderTextView: TextView
    private lateinit var notFoundImageView: ImageView
    private lateinit var noConnectionImageView: ImageView

    // Adapter for displaying search results in RecyclerView
    private lateinit var adapter: RecipeAdapter

    companion object {
        private const val TAG = "SearchFragment"
    }

    /**
     * Inflates the layout for this fragment and initializes the UI components.
     * Sets up RecyclerView, SearchView, and ViewModel.
     *
     * @param inflater LayoutInflater object to inflate the layout
     * @param container ViewGroup that will contain the fragment's view
     * @param savedInstanceState Bundle containing saved instance state
     * @return The root view of the fragment's layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")

        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize UI components
        searchView = view.findViewById(R.id.search_view)
        recipeRecyclerView = view.findViewById(R.id.recipeRecyclerView)
        placeholderTextView = view.findViewById(R.id.placeholder_text_view)
        notFoundImageView = view.findViewById(R.id.not_found_image)
        noConnectionImageView = view.findViewById(R.id.no_connection_image) // Add this line

        // Configure SearchView
        searchView.queryHint = "Search for a recipe"
        searchView.onActionViewExpanded()
        searchView.clearFocus()

        // Set up RecyclerView with LinearLayoutManager and adapter
        recipeRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecipeAdapter { meal -> navigateToDetail(meal) }
        recipeRecyclerView.adapter = adapter

        // Initialize ViewModel with SearchRepository
        val repository = SearchRepositoryImpl()
        val factory = SearchViewModelFactory(repository)
        searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        // Check for internet connection and update UI accordingly
        if (!isInternetAvailable()) {
            noConnectionImageView.visibility = View.VISIBLE
            searchView.visibility = View.GONE
            placeholderTextView.visibility = View.GONE
            recipeRecyclerView.visibility = View.GONE
            notFoundImageView.visibility = View.GONE
        } else {
            // Set initial visibility of UI components based on query
            if (searchView.query.isEmpty()) {
                placeholderTextView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
                notFoundImageView.visibility = View.GONE
                noConnectionImageView.visibility = View.GONE
            }
        }

        // Set up SearchView listener to handle text changes
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query submission (not used here, live search is handled)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    Log.d(TAG, "Query text changed: $it")
                    // Update UI based on the new text input
                    if (it.isEmpty()) {
                        // Show placeholder when query is empty
                        placeholderTextView.visibility = View.VISIBLE
                        recipeRecyclerView.visibility = View.GONE
                        notFoundImageView.visibility = View.GONE
                    } else {
                        // Hide placeholder and perform search
                        placeholderTextView.visibility = View.GONE
                        searchViewModel.searchMealByName(it)
                    }
                }
                return true
            }
        })

        return view
    }

    /**
     * Sets up observers for LiveData from the ViewModel after the view has been created.
     * Observers handle updates to meal data and error messages.
     *
     * @param view The root view of the fragment's layout
     * @param savedInstanceState Bundle containing saved instance state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        // Observe meals LiveData from ViewModel
        searchViewModel.meals.observe(viewLifecycleOwner) { meals ->
            Log.d(TAG, "Updating UI with meals: $meals")
            // Update UI based on the list of meals
            if (meals.isEmpty() && searchView.query.isNotEmpty()) {
                // Show "not found" image when no meals are found
                notFoundImageView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
            } else if (meals.isNotEmpty()) {
                // Show RecyclerView and update adapter with new data
                notFoundImageView.visibility = View.GONE
                recipeRecyclerView.visibility = View.VISIBLE
                adapter.updateData(meals)
            } else if (searchView.query.isEmpty()) {
                // Show placeholder when query is empty
                placeholderTextView.visibility = View.VISIBLE
                recipeRecyclerView.visibility = View.GONE
                notFoundImageView.visibility = View.GONE
            }
        }

        // Observe error messages LiveData from ViewModel
        searchViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Log.d(TAG, "Error message received: $error")
            // Show "not found" image on error
            notFoundImageView.visibility = View.VISIBLE
            recipeRecyclerView.visibility = View.GONE
        }
    }

    /**
     * Navigates to the RecipeDetailFragment with the selected meal.
     *
     * @param meal The Meal object to display in the detail fragment.
     */
    private fun navigateToDetail(meal: Meal) {
        Log.d("SearchFragment", "Navigating to details with meal: $meal")
        val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal)
        findNavController().navigate(action)
    }

    /**
     * Checks if the device has an active internet connection.
     *
     * @return True if there is an internet connection, false otherwise.
     */
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
