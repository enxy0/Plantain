package kek.enxy.plantwriter.presentation.common.extensions

import android.widget.EditText

fun EditText.setEndSelection() = post { setSelection(text.length) }
