package kek.plantain.utils.extensions

fun Array<ByteArray>.slice(block: Int, range: IntRange) =
    this[block].copyOfRange(range.first, range.last + 1)