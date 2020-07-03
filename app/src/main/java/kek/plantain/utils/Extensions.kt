package kek.plantain.utils

import android.nfc.tech.MifareClassic

fun Array<ByteArray>.pretty() = this.joinToString(prefix = "(", postfix = "\n)") {
    "\n${it.joinToString(
        prefix = "(",
        postfix = ")"
    )}"
}

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

fun ByteArray.extractValue(): Int {
    var result = 0
    for (i in this.indices) {
        val shift = if (i == 0) 0 else 2 shl i + 1
        result += this[i].toInt() and 0xFF shl shift
    }
    return result
}

fun Array<ByteArray>.slice(block: Int, from: Int, to: Int) =
    this[block].copyOfRange(from, to + 1)

/**
 * Connects to the given [tag], invokes [fn] in context of [tag], closes [tag] and returns the result.
 */
inline fun <T> runUsing(tag: MifareClassic, fn: MifareClassic.() -> T): T {
    tag.connect()
    val result = fn.invoke(tag)
    tag.close()
    return result
}