package kek.enxy.data.readwrite

import android.nfc.tech.MifareClassic
import kek.enxy.data.model.Sector
import kek.enxy.data.readwrite.model.*

class ReadWriteDataSourceImpl : ReadWriteDataSource{
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

    override fun getDumpFromSectors(sector4: Sector, sector5: Sector): Dump =
        Dump(
            balance = getBalanceFromSector(sector4),
            lastUseAmount = getLastUseAmount(sector5),
            lastUseDate = getLastUseDate(sector5),
            lastPaymentAmount = getLastPaymentAmount(sector4),
            lastPaymentDate = getLastPaymentDate(sector4),
            groundTravelTotal = getGroundTravelTotal(sector5),
            undergroundTravelTotal = getSubwayTravelTotal(sector5)
        )

    private fun getBalanceFromSector(sector4: Sector): Rubles {
        val raw = sector4.data[0]
            .copyOfRange(0, 4)
            .extractValue()
        return Rubles(raw)
    }

    private fun getLastPaymentDate(sector4: Sector): AppDate {
        val raw = sector4.data[2]
            .copyOfRange(2, 5)
            .extractValue()
        return AppDate(raw)
    }

    private fun getLastPaymentAmount(sector4: Sector): Rubles {
        val raw = sector4.data[2]
            .copyOfRange(8, 11)
            .extractValue()
        return Rubles(raw)
    }

    private fun getLastUseDate(sector5: Sector): AppDate {
        val raw = sector5.data[0]
            .copyOfRange(0, 3)
            .extractValue()
        return AppDate(raw)
    }

    private fun getLastUseAmount(sector5: Sector): Rubles {
        val raw = sector5.data[0]
            .copyOfRange(6, 8)
            .extractValue()
        return Rubles(raw)
    }

    private fun getGroundTravelTotal(sector5: Sector): Count {
        val raw = sector5.data[1]
            .copyOfRange(1, 2)
            .extractValue()
        return Count(raw)
    }

    private fun getSubwayTravelTotal(sector5: Sector): Count {
        val raw = sector5.data[1]
            .copyOfRange(0, 1)
            .extractValue()
        return Count(raw)
    }

    /**
     * Extracts [Int] value from the given [ByteArray]
     * @receiver ByteArray
     * @return Int
     */
    private fun ByteArray.extractValue(): Int = foldIndexed(0) { index, acc, byte ->
        val shift = if (index == 0) 0 else 2 shl index + 1
        acc + ((byte.toInt() and 0xFF) shl shift)
    }
}
