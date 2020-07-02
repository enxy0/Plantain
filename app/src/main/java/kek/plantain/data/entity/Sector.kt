package kek.plantain.data.entity

import android.nfc.tech.MifareClassic
import android.util.Log
import kek.plantain.utils.pretty
import java.io.IOException

class Sector {
    @OptIn(ExperimentalUnsignedTypes::class)
    var data: Array<UByteArray> = Array(SECTOR_SIZE) { UByteArray(
        BLOCK_SIZE
    ) }

    companion object {
        private const val TAG = "Sector"
        const val SECTOR_SIZE = 4
        const val BLOCK_SIZE = MifareClassic.BLOCK_SIZE
    }

    @Throws(IOException::class)
    fun read(mifareTag: MifareClassic, block: Int) {
        for (i in 0 until SECTOR_SIZE) {
            Log.d(TAG, "read: data.pretty=${data.pretty()}")
            data[i] = mifareTag.readBlock(block + i).toUByteArray()
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun slice(block: Int, from: Int, to: Int) = data[block]
        .copyOfRange(from, to + 1)
        .reversedArray()
}