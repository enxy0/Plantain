package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class AppDate(
    val raw: Int,
    val value: String = formatDate(raw)
) : Parcelable {

    companion object {
        private const val DATE_PATTERN = "dd.MM.yyyy HH:mm"

        private val initialDate: Calendar
            get() = Calendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply {
                this[2010, 0, 1, 0, 0] = 0
            }

        private fun formatDate(minutesSince2010: Int): String {
            val calendar = initialDate.apply { add(Calendar.MINUTE, minutesSince2010) }
            return SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(calendar.time)
        }
    }

    override fun toString(): String = value
}
