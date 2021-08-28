package kek.enxy.plantwriter.presentation.common.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.getParentAsListenerOrNull(): T? = when {
    parentFragment is T -> parentFragment as? T
    activity is T -> activity as? T
    else -> null
}

inline fun <reified T> Fragment.getParentAsListener(): T = getParentAsListenerOrNull<T>()!!

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
fun Fragment.toast(@StringRes resId: Int) = Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()

fun Fragment.longToast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
fun Fragment.longToast(@StringRes resId: Int) = Toast.makeText(requireContext(), resId, Toast.LENGTH_LONG).show()
