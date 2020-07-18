package kek.plantain.data.entity

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

data class Date(val raw: Int) {
    private val formatted: String = formatDate(raw)

    override fun toString(): String = formatted

    companion object {
        /**
         * Initial date for Plantain timestamps
         */
        val initialDate: Calendar
            get () = Calendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply {
                this[2010, 0, 1, 0, 0] = 0
            }

        const val DATE_PATTERN = "dd.MM.yyyy HH:mm"

        fun isValid(input: String): Boolean =
            input.matches("^\\d\\d.\\d\\d.\\d\\d\\d\\d \\d\\d:\\d\\d$".toRegex())
    }

    private fun formatDate(minutesSince2010: Int): String {
        val calendar = initialDate.apply { add(Calendar.MINUTE, minutesSince2010) }
        return SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(calendar.time)
    }
}

fun Int.toDate() = Date(this)

fun Long.toDate() = Date(this.toInt())

fun String.toDate(): Date {
    val newDate = Calendar.getInstance().also {
        it.time = SimpleDateFormat(Date.DATE_PATTERN, Locale.getDefault()).parse(this)!!
        it.set(Calendar.MINUTE, it.get(Calendar.MINUTE) + 1)
    }
    val minutes = Duration.between(Date.initialDate.toInstant(), newDate.toInstant()).toMinutes()
    return minutes.toDate()
}

fun String.toDateOr(default: Date): Date =
    if (Date.isValid(this)) toDate() else default
