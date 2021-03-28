package kek.enxy.plantwriter.presentation.common

import android.content.Context
import android.content.Intent
import android.net.Uri

object IntentUtils {
    fun openLink(context: Context, url: String) {
        if (url.isNotBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }
}
