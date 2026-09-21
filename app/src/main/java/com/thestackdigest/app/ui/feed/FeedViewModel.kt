package com.thestackdigest.app.ui.feed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(): ViewModel() {

    private val allArticles = fakeArticles

    private val _uiState = MutableStateFlow(
        FeedUiState(
            articles = allArticles
        )
    )

    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    fun onCategorySelected(category: String){
        val filteredArticles = if (category == "All") {
            allArticles
        } else {
            allArticles.filter { article ->
                category in article.categories
            }
        }

        _uiState.update {
            it.copy(
                selectedCategory = category,
                articles = filteredArticles
            )
        }
    }
}