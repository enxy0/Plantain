package kek.enxy.plantwriter.presentation.common.extensions

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
