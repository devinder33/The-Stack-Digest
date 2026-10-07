package com.thestackdigest.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thestackdigest.app.domain.repository.SettingsRepository
import com.thestackdigest.app.ui.settings.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val appTheme: StateFlow<AppTheme> =
        settingsRepository.appTheme
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppTheme.SYSTEM
            )
}