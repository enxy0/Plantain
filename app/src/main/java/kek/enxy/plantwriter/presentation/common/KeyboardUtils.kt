package kek.enxy.plantwriter.presentation.common

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {
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
        val token = editText.windowToken
        if (token != null) {
            val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(token, 0)
        }
    }

    fun toggle(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun show(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
