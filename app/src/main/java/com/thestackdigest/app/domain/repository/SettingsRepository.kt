package com.thestackdigest.app.domain.repository
import com.thestackdigest.app.ui.settings.AppTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val appTheme: Flow<AppTheme>

    suspend fun setAppTheme(
        appTheme: AppTheme
    )
}