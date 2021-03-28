package kek.enxy.plantwriter.presentation.common.extensions

import android.view.View
import androidx.core.view.isVisible

fun <T : View, D> T.fillWhenHasData(data: D?, onNonNullData: T.(data: D) -> Unit) {
    val shouldShowView = if (data is String) {
        data.isNotBlank()
    } else {
        data != null
    }
    isVisible = shouldShowView
    if (shouldShowView) {
        onNonNullData(data!!)
    }
}
