package kek.enxy.plantwriter.presentation.common.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attr: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attr, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.getResourceFromAttr(
    @AttrRes attr: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attr, typedValue, resolveRefs)
    return typedValue.resourceId
}
