package kek.enxy.plantwriter.presentation.common

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlin.time.Duration

object KeyboardUtils {
    fun show(view: View, delay: Duration) {
        val scope = view.findViewTreeLifecycleOwner()?.lifecycleScope
        scope?.launchWhenResumed {
            delay(delay)
            showInternal(view)
        } ?: showInternal(view)
    }

    private fun showInternal(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
        } else {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
