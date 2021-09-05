package kek.enxy.plantwriter.presentation.settings

import androidx.lifecycle.ViewModel
import kek.enxy.data.settings.model.AppTheme
import kek.enxy.domain.settings.AppSettings

class SettingsViewModel(
    private val appSettings: AppSettings
) : ViewModel() {
    val darkThemeFlow = appSettings.darkThemeFlow

    fun setDarkTheme(isDark: Boolean) {
        appSettings.darkTheme = if (isDark) AppTheme.DARK else AppTheme.LIGHT
    }
}
