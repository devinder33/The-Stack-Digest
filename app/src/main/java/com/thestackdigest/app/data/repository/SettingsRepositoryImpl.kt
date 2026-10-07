package com.thestackdigest.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thestackdigest.app.domain.repository.SettingsRepository
import com.thestackdigest.app.ui.settings.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(
    name = "settings"
)

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    override val appTheme: Flow<AppTheme> =
        context.settingsDataStore.data
            .map { preferences ->

                val savedTheme =
                    preferences[APP_THEME_KEY]

                when (savedTheme) {

                    AppTheme.LIGHT.name ->
                        AppTheme.LIGHT

                    AppTheme.DARK.name ->
                        AppTheme.DARK

                    else ->
                        AppTheme.SYSTEM
                }
            }

    override suspend fun setAppTheme(
        appTheme: AppTheme
    ) {

        context.settingsDataStore.edit { preferences ->

            preferences[APP_THEME_KEY] =
                appTheme.name
        }
    }

    companion object {

        private val APP_THEME_KEY =
            stringPreferencesKey(
                "app_theme"
            )
    }
}