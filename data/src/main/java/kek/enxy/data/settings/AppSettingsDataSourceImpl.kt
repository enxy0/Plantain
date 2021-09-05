package kek.enxy.data.settings

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import kek.enxy.data.settings.model.AppTheme

class AppSettingsDataSourceImpl : KotprefModel(), AppSettingsDataSource {
    override var darkTheme: AppTheme by enumValuePref(AppTheme.getDefaultTheme())
}
