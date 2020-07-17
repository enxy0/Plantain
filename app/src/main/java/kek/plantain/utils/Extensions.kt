package kek.plantain.utils

import android.content.Context
import android.nfc.tech.MifareClassic
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import kek.plantain.data.entity.Count
import kek.plantain.data.entity.Date
import kek.plantain.data.entity.Rubles
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

val hexArray = "0123456789ABCDEF".toCharArray()

fun ByteArray.toHex(): String {
    val hexChars = CharArray(size * 2)
    for (i in indices) {
        val v: Int = this[i].toInt() and 0xFF
        hexChars[i * 2] = hexArray[v ushr 4]
        hexChars[i * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}

fun String.hexToBytes(): ByteArray {
    val len = length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(this[i], 16) shl 4)
                + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

fun ByteArray.extractValue(): Int = foldIndexed(0) { index, acc, byte ->
    val shift = if (index == 0) 0 else 2 shl index + 1
    acc + ((byte.toInt() and 0xFF) shl shift)
}

fun ByteArray.writeValue(value: Int, range: IntRange) {
    val bytes = ByteBuffer.allocate(4)
        .putInt(Integer.reverseBytes(value))
        .array()
    var j = 0
    for (i in range) {
        this[i] = bytes[j++]
    }
}

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

/**
 * Connects to the given [tag], invokes [fn] in context of [tag], closes [tag] and returns the result.
 */
inline fun <T> runUsing(tag: MifareClassic, fn: MifareClassic.() -> T): T {
    tag.connect()
    val result = fn.invoke(tag)
    tag.close()
    return result
}

/**
 * Updates MutableLiveData value after applied changes in fn().
 * Useful when you need to update specific field of LiveData.value and to notify the observers.
 */
inline fun <V> MutableLiveData<Result<V, Exception>>.update(fn: V.() -> Unit) {
    postValue(value?.map { result -> result.apply(fn) })
}

fun toast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}