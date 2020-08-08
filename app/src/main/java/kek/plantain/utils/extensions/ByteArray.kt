package kek.plantain.utils.extensions

import java.nio.ByteBuffer
import kotlin.experimental.inv

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

/**
 * Value blocks are stored in a fixed data format:
 *
 * 0-3: Value, 4-7: Inverted Value, 8 - 11: Value, 12: address, 13: inverted address, 14: address, 15: inverted address
 *
 * Important: balance of the Plantain card is stored as a value block.
 * @param value 4-byte signed value
 * @see <a href="https://www.nxp.com/docs/en/data-sheet/MF1S50YYX_V1.pdf">Mifare Classic Documentation (page 9, 8.6.2.1 Value blocks)</a>
 */
fun ByteArray.writeValueBlock(value: Int) {
    val bytes = ByteBuffer.allocate(4)
            .putInt(Integer.reverseBytes(value))
            .array()
            .copyOfRange(0, 4) // value must fit in 4 bytes

    val invertedBytes = bytes.map { it.inv() }.toByteArray()

                                                                  // 00 FF 00 FF
    val block = bytes + invertedBytes + bytes + byteArrayOf(0, -1, 0, -1)

    for (i in block.indices) {
        this[i] = block[i]
    }
}
