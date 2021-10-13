package kek.enxy.plantwriter.presentation.common

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.IBinder
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.postDelayed

object KeyboardUtils {
    private const val KEYBOARD_FOCUS_DELAY = 300L

    fun hide(activity: Activity?, windowToken: IBinder? = null) {
        val token = windowToken ?: activity?.currentFocus?.windowToken
        if (token != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(token, 0)
        }
    }

    /**
     * Call before onDestroyView
     * @see androidx.fragment.app.Fragment.onDestroyView
     */
    fun hide(editText: EditText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            editText.windowInsetsController?.hide(WindowInsets.Type.ime())
        } else {
            val token = editText.windowToken
            if (token != null) {
                val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(token, 0)
            }
        }
    }

    fun toggle(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun show(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
        } else {
            view.postDelayed(KEYBOARD_FOCUS_DELAY) { // TODO: workaround, should be replaced
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}
