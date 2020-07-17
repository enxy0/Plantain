package kek.plantain.utils.extensions

import java.nio.ByteBuffer

fun ByteArray.toHex(): String {
    val hexArray = "0123456789ABCDEF".toCharArray()
    val hexChars = CharArray(size * 2)
    for (i in indices) {
        val v: Int = this[i].toInt() and 0xFF
        hexChars[i * 2] = hexArray[v ushr 4]
        hexChars[i * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
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
