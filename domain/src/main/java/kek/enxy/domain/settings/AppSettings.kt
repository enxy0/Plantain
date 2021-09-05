package kek.enxy.domain.settings

import kek.enxy.data.settings.model.AppTheme
import kotlinx.coroutines.flow.StateFlow

interface AppSettings {
    val darkThemeFlow: StateFlow<AppTheme>
    var darkTheme: AppTheme

    fun init()
}
