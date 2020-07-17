package kek.plantain.utils

import kek.plantain.data.entity.Count
import kek.plantain.data.entity.Date
import kek.plantain.data.entity.Rubles
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

private fun String.toRubles(): Rubles {
    return (toInt() * 100).toRubles()
}

fun String.toRublesOr(default: Rubles): Rubles =
    if (Rubles.isValid(this)) toRubles() else default

fun Int.toRubles() = Rubles(this)

fun Int.toDate() = Date(this)

fun Long.toDate() = Date(this.toInt())

fun String.toDate(): Date {
    val initDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+3")).apply {
        this[2010, 0, 1, 0, 0] = 0
    }
    val newDate = Calendar.getInstance().also {
        it.time = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).parse(this)!!
        it.set(Calendar.MINUTE, it.get(Calendar.MINUTE) + 1)
    }
    val minutes = Duration.between(initDate.toInstant(), newDate.toInstant()).toMinutes()
    return minutes.toDate()
}

fun String.toDateOr(default: Date): Date =
    if (Date.isValid(this)) toDate() else default

fun Int.toCount() = Count(this)

private fun String.toCount() = Count(this.toInt())

fun String.toCountOr(default: Count) = if (Count.isValid(this)) toCount() else default

fun Array<ByteArray>.slice(block: Int, range: IntRange) =
    this[block].copyOfRange(range.first, range.last + 1)