package com.thestackdigest.app.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import com.thestackdigest.app.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: FeedRepository
) : ViewModel() {

    private val articleId: String =
        checkNotNull(
            savedStateHandle[Routes.ARTICLE_ID]
        )

    val article: StateFlow<Article?> =
        repository
            .observeArticle(articleId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

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