package com.example.recipeapp.Recipe.Search.searchviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Recipe.Search.repo.SearchRepository

/**
 * Factory class for creating instances of SearchViewModel.
 *
 * This factory allows us to provide dependencies to the ViewModel, such as a repository.
 */
class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`, if it is a `SearchViewModel`.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return A new instance of the `SearchViewModel`.
     * @throws IllegalArgumentException If the `modelClass` is not a `SearchViewModel`.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}