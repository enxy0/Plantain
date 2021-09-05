package kek.enxy.domain.settings

import kek.enxy.data.common.ThemeUtils
import kek.enxy.data.settings.AppSettingsDataSource
import kek.enxy.data.settings.model.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppSettingsImpl(
    private val appSettingsDataSource: AppSettingsDataSource
) : AppSettings {
    override var darkTheme: AppTheme = appSettingsDataSource.darkTheme
        get() = appSettingsDataSource.darkTheme
        set(value) {
            field = value
            _darkThemeStateFlow.value = value
            appSettingsDataSource.darkTheme = value
            setAppTheme()
        }

    private val _darkThemeStateFlow = MutableStateFlow(darkTheme)
    override val darkThemeFlow = _darkThemeStateFlow.asStateFlow()

    override fun init() {
        setAppTheme()
    }

    private fun setAppTheme() {
        ThemeUtils.setNightMode(darkTheme)
    }
}
