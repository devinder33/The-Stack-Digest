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
) : ViewModel() {

    private var allArticles: List<Article> = emptyList()

    private val _uiState = MutableStateFlow(
        FeedUiState()
    )

    val uiState: StateFlow<FeedUiState> =
        _uiState.asStateFlow()

    init {
        observeArticles()
        refreshArticles()
    }

    private fun observeArticles() {

        viewModelScope.launch {

            repository
                .observeArticles()
                .collect { articles ->

                    allArticles = articles

                    val selectedCategory =
                        _uiState.value.selectedCategory

                    val filteredArticles =
                        articles.filter { article ->
                            matchesCategory(
                                article,
                                selectedCategory
                            )
                        }

                    _uiState.update {
                        it.copy(
                            articles = filteredArticles
                        )
                    }
                }
        }
    }

    private fun refreshArticles() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {

                repository.refreshArticles()

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }

            } finally {

                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onCategorySelected(category: String) {

        val filteredArticles =
            allArticles.filter { article ->
                matchesCategory(
                    article,
                    category
                )
            }

        _uiState.update {
            it.copy(
                selectedCategory = category,
                articles = filteredArticles
            )
        }
    }

    private fun matchesCategory(
        article: Article,
        category: String
    ): Boolean {

        return when (category) {

            "All" -> true

            "Android" -> {
                article.sourceName == "Android Developers"
            }

            "Kotlin" -> {
                article.sourceName == "Kotlin Blog"
            }

            else -> {
                article.categories.any {
                    it.equals(
                        category,
                        ignoreCase = true
                    )
                }
            }
        }
    }

    fun onBookmarkClicked(
        article: Article
    ) {

        viewModelScope.launch {

            repository.setArticleSaved(
                articleId = article.id,
                isSaved = !article.isSaved
            )
        }
    }
}