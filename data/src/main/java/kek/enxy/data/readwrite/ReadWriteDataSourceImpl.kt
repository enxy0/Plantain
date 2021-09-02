package kek.enxy.data.readwrite

import android.nfc.tech.MifareClassic
import kek.enxy.data.model.Sector
import kek.enxy.data.readwrite.model.*
import java.nio.ByteBuffer
import kotlin.experimental.inv

class ReadWriteDataSourceImpl : ReadWriteDataSource {
    override fun writeSector(mifareClassic: MifareClassic, sector: Sector) {
        val block = mifareClassic.sectorToBlock(sector.index)
        val blocksCount = mifareClassic.getBlockCountInSector(sector.index)
        for (i in 0 until blocksCount - 1) {
            mifareClassic.writeBlock(block + i, sector.data[i])
        }
    }

    override fun getSector(mifareClassic: MifareClassic, sectorId: Int): Sector {
        val block = mifareClassic.sectorToBlock(sectorId)
        val blocksCount = mifareClassic.getBlockCountInSector(sectorId)
        val data = Array(blocksCount) { ByteArray(MifareClassic.BLOCK_SIZE) }
        for (i in 0 until blocksCount) {
            data[i] = mifareClassic.readBlock(block + i)
        }
        return Sector(sectorId, data)
    }

    override fun getDumpFromSectors(
        uid: String,
        sector4: Sector,
        sector5: Sector
    ): Dump = Dump(uid, sector4, sector5)
}
