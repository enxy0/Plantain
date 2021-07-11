package kek.enxy.plantwriter.presentation.common.extensions

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.getParentAsListenerOrNull(): T? = when {
    parentFragment is T -> parentFragment as? T
    activity is T -> activity as? T
    else -> null
}

inline fun <reified T> Fragment.getParentAsListener(): T = getParentAsListenerOrNull<T>()!!
