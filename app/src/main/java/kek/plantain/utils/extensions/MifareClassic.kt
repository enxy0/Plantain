package kek.plantain.utils.extensions

import android.nfc.tech.MifareClassic

/**
 * Connects to the given [MifareClassic] tag, invokes [fn] in context of [MifareClassic] tag,
 * closes [MifareClassic] tag and then returns the result.
 */
inline fun <T> MifareClassic.using(fn: MifareClassic.() -> T): T {
    connect()
    val result = fn.invoke(this)
    close()
    return result
}