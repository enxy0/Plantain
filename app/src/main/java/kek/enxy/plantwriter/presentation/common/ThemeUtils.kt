package kek.enxy.plantwriter.presentation.common

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    fun setNightMode(isDark: Boolean) {
        val nightMode = if (isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
