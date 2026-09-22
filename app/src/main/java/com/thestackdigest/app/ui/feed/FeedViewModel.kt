package com.thestackdigest.app.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
): ViewModel() {

    private var allArticles: List<Article> = emptyList()


    private val _uiState = MutableStateFlow(
        FeedUiState()
    )

    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                allArticles = repository.getArticles()

                _uiState.update {
                    it.copy(
                        articles = allArticles,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun onCategorySelected(category: String){
        val filteredArticles = if (category.equals("All", ignoreCase = true)) {
            allArticles
        } else {
            allArticles.filter { article ->
                article.categories.any {
                    it.equals(category, ignoreCase = true)
                }
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