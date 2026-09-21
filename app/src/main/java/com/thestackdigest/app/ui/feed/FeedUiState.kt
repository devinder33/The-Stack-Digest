package com.thestackdigest.app.ui.feed

import com.thestackdigest.app.domain.model.Article

data class FeedUiState(
    val articles: List<Article> = emptyList(),
    val selectedCategory: String = "All",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)