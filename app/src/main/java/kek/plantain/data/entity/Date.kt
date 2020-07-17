package kek.plantain.data.entity

import java.text.SimpleDateFormat
import java.util.*

data class Date(val raw: Int) {
    private val formatted: String = createDate(raw)

    override fun toString(): String = formatted

    companion object {
        fun isValid(input: String): Boolean =
            input.matches("^\\d\\d.\\d\\d.\\d\\d\\d\\d \\d\\d:\\d\\d$".toRegex())
    }

    private fun createDate(minutesSince2010: Int): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
        calendar[2010, 0, 1, 0, 0] = 0
        calendar.add(Calendar.MINUTE, minutesSince2010)
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(calendar.time)
    }
}