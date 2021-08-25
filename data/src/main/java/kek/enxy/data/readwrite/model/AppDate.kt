package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@Parcelize
data class AppDate(
    val raw: Int,
    val value: String = formatDate(raw)
) : Parcelable {

    companion object {
        private const val DATE_PATTERN = "dd.MM.yyyy HH:mm"

//        fun fromDate(date: String): AppDate {
//            val newDate = Calendar.getInstance().also {
//                it.time = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(date)!!
//                it.set(Calendar.MINUTE, it.get(Calendar.MINUTE) + 1)
//            }
//            val minutes = Duration.between(initialDate.toInstant(), newDate.toInstant()).toMinutes()
//            return AppDate(minutes.toInt())
//        }
//
//        fun fromDate(date: LocalDateTime): AppDate {
//            val minutes = ChronoUnit.MINUTES.between(initialDate, date)
//            val minutes = Duration.between(initialDate.toInstant(), date.toInstant(ZoneOffset.ofHours).toMinutes()
//            return AppDate(minutes.toInt())
//        }
//
//        fun now(): AppDate = fromDate(LocalDateTime.now())
//
//        fun yesterday(): AppDate {
//            val now = LocalDateTime.now()
//            return fromDate(now.minusDays(1L))
//        }
//
//        fun weekAgo(): AppDate {
//            val now = LocalDateTime.now()
//            return fromDate(now.minusDays(7L))
//        }

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
