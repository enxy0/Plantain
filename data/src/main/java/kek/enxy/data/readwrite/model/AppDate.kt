package kek.enxy.data.readwrite.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Keep
@Parcelize
@Serializable
data class AppDate(
    val raw: Int,
    val value: String = formatDate(raw)
) : Parcelable {

    companion object {
        private const val DATE_PATTERN = "dd.MM.yyyy HH:mm"
        private val initialDateTime: LocalDateTime get() = LocalDateTime.of(2010, 1, 1, 0, 0)

        fun fromDate(date: String): AppDate {
            val newDate = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(date)!!
            val zoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
            val minutes = ChronoUnit.MINUTES.between(
                initialDateTime.toInstant(zoneOffset),
                newDate.toInstant()
                )
            return AppDate(minutes.toInt())
        }

        fun now(): AppDate = fromDate(LocalDateTime.now())

        fun yesterday(): AppDate {
            val now = LocalDateTime.now()
            return fromDate(now.minusDays(1L))
        }

        fun weekAgo(): AppDate {
            val now = LocalDateTime.now()
            return fromDate(now.minusDays(7L))
        }

        private fun fromDate(date: LocalDateTime): AppDate {
            val minutes = ChronoUnit.MINUTES.between(initialDateTime, date)
            return AppDate(minutes.toInt())
        }

        private fun formatDate(minutesSince2010: Int, pattern: String = DATE_PATTERN): String {
            return initialDateTime
                .plusMinutes(minutesSince2010.toLong())
                .format(DateTimeFormatter.ofPattern(pattern))
        }
    }

    fun getFormattedDate(pattern: String): String = formatDate(raw, pattern)

    operator fun compareTo(other: AppDate) = raw.compareTo(other.raw)

    override fun toString(): String = value
}
