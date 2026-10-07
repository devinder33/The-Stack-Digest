package com.thestackdigest.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thestackdigest.app.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val appTheme: StateFlow<AppTheme> =
        settingsRepository.appTheme
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppTheme.SYSTEM
            )

    fun onThemeSelected(
        appTheme: AppTheme
    ) {
        viewModelScope.launch {
            settingsRepository.setAppTheme(
                appTheme
            )
        }
    }
}