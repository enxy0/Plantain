package kek.plantain.utils.extensions

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import kek.plantain.data.entity.Sector
import java.io.IOException

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

inline fun Tag.getMifareTagOr(fallback: (Tag) -> Tag ): MifareClassic =
    try {
        MifareClassic.get(this)
    } catch (e: Exception) {
        MifareClassic.get(fallback(this))
    }


@Throws(IOException::class)
fun MifareClassic.getSector(sectorId: Int): Sector {
    val block = sectorToBlock(sectorId)
    val blocksCount = getBlockCountInSector(sectorId)
    val data = Array(blocksCount) { ByteArray(MifareClassic.BLOCK_SIZE) }
    for (i in 0 until blocksCount) {
        data[i] = readBlock(block + i)
    }
    return Sector(sectorId, data)
}

@Throws(IOException::class)
fun MifareClassic.writeFrom(sector: Sector) {
    val block = sectorToBlock(sector.sectorId)
    val blocksCount = getBlockCountInSector(sector.sectorId)
    for (i in 0 until blocksCount - 1) { // last block contains information about keys, excluding it
        writeBlock(block + i, sector.data[i])
    }
}