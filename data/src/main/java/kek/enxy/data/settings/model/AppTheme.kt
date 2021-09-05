package kek.enxy.data.settings.model

import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatDelegate

@Keep
enum class AppTheme {
    LIGHT, DARK;

    companion object {
        fun getDefaultTheme() = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES,
            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> DARK
            else -> LIGHT
        }
    }
}
