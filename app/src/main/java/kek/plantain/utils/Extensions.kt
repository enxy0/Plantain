package kek.plantain.utils

import java.util.*

internal fun String.hexToInt() = this
    .chunked(2)
    .reversed()
    .joinToString(separator = "")
    .toInt(16)

@OptIn(ExperimentalUnsignedTypes::class)
internal fun ByteArray.toHex() =
    this.joinToString(separator = "") { it.toUByte().toString(16) }
        .toUpperCase(Locale.getDefault())

@OptIn(ExperimentalUnsignedTypes::class)
internal fun UByteArray.extractNumber(radix: Int) = this
    .joinToString("") { it.toString(radix) }
    .toInt(radix)

@OptIn(ExperimentalUnsignedTypes::class)
internal fun arrayTwoDimOfUBytes(sizeOuter: Int, sizeInner: Int): Array<UByteArray> =
    Array(sizeOuter) { UByteArray(sizeInner) }

fun<T> Array<T>.pretty() = this.joinToString { "\n$it" }