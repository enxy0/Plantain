package kek.plantain.data.entity

import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareClassic.BLOCK_SIZE
import java.io.IOException
class Sector {
    var data: Array<ByteArray> = Array(SECTOR_SIZE) { ByteArray(BLOCK_SIZE) }

    companion object {
        private const val TAG = "Sector"
        const val SECTOR_SIZE = 4
    }

    @Throws(IOException::class)
    fun read(mifareTag: MifareClassic, sectorId: Int) {
        val block = mifareTag.sectorToBlock(sectorId)
        for (i in 0 until SECTOR_SIZE) {
            data[i] = mifareTag.readBlock(block + i)
        }
    }

    @Throws(IOException::class)
    fun write(mifareTag: MifareClassic, sectorId: Int) {
        val block = mifareTag.sectorToBlock(sectorId)
        for (i in 0 until SECTOR_SIZE - 1) {
            mifareTag.writeBlock(block + i, data[i])
        }
    }

    override fun toString(): String {
        val indention = "    "
        return data.joinToString(separator = "\n", prefix = "(\n", postfix = "\n)") {
            indention + it.joinToString(prefix = "(", postfix = ")")
        }
    }
}