package com.thestackdigest.app.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thestackdigest.app.domain.model.Article
import com.thestackdigest.app.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: FeedRepository
): ViewModel() {

    val savedArticles: StateFlow<List<Article>> =
        repository.observeSavedArticles()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
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