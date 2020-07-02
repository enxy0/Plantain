package kek.plantain.data.entity

import android.nfc.tech.MifareClassic
import java.io.IOException

class Sector {
    @OptIn(ExperimentalUnsignedTypes::class)
    var data: Array<ByteArray> = Array(SECTOR_SIZE) { ByteArray(BLOCK_SIZE) }

    companion object {
        private const val TAG = "Sector"
        const val SECTOR_SIZE = 4
        const val BLOCK_SIZE = MifareClassic.BLOCK_SIZE
    }

    @Throws(IOException::class)
    fun read(mifareTag: MifareClassic, block: Int) {
        for (i in 0 until SECTOR_SIZE) {
            data[i] = mifareTag.readBlock(block + i)
        }
    }
}