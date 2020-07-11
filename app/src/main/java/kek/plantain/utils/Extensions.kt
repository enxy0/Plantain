package kek.plantain.utils

import android.nfc.tech.MifareClassic
import kek.plantain.data.entity.Rubles
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*

val hexArray = "0123456789ABCDEF".toCharArray()

fun ByteArray.toHex(): String {
    val hexChars = CharArray(this.size * 2)
    for (i in this.indices) {
        val v: Int = this[i].toInt() and 0xFF
        hexChars[i * 2] = hexArray[v ushr 4]
        hexChars[i * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}

fun String.hexToBytes(): ByteArray {
    val len = this.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(this[i], 16) shl 4)
            + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

fun Int.toRubles() = Rubles(this)

fun ByteArray.extractValue(): Int = this.foldIndexed(0) { index, acc, byte ->
    val shift = if (index == 0) 0 else 2 shl index + 1
    acc + ((byte.toInt() and 0xFF) shl shift)
}

fun ByteArray.extractDate(): String {
    val timeDiff = this.extractValue()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
    calendar[2010, 0, 1, 0, 0] = 0
    calendar.add(Calendar.MINUTE, timeDiff)
    return SimpleDateFormat("dd.MM.yy (HH:mm)", Locale.getDefault()).format(calendar.time)
}

fun Int.writeValue(to: ByteArray) {
    val bytes = ByteBuffer.allocate(4)
        .putInt(Integer.reverseBytes(this))
        .array()
    for (i in bytes.indices) {
        to[i] = bytes[i]
    }
}

fun Array<ByteArray>.slice(block: Int, range: IntRange) =
    this[block].copyOfRange(range.first, range.last)

/**
 * Connects to the given [tag], invokes [fn] in context of [tag], closes [tag] and returns the result.
 */
inline fun <T> runUsing(tag: MifareClassic, fn: MifareClassic.() -> T): T {
    tag.connect()
    val result = fn.invoke(tag)
    tag.close()
    return result
}