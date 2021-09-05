package kek.enxy.data.common

import androidx.appcompat.app.AppCompatDelegate
import kek.enxy.data.settings.model.AppTheme

object ThemeUtils {
    fun setNightMode(theme: AppTheme) {
        val nightMode = if (theme == AppTheme.DARK) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
