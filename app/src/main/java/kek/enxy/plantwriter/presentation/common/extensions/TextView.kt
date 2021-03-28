package kek.enxy.plantwriter.presentation.common.extensions

import android.annotation.SuppressLint
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Appends [newText] at the end of the text with [newLine] if needed.
 * @receiver TextView
 * @param newText String text to be appended
 * @param newLine Boolean adds "\n" before appending [newText]
 */
@SuppressLint("SetTextI18n")
fun TextView.logText(newText: String, newLine: Boolean = true) {
    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
    val newLineChar = if (newLine) "\n" else ""
    text = "$text$newLineChar" + "$time: $newText"
}
