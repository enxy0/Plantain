package kek.enxy.data.readwrite

import android.nfc.tech.MifareClassic
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.data.model.Sector

interface ReadWriteDataSource {
    fun writeSector(mifareClassic: MifareClassic, sector: Sector)
    fun getSector(mifareClassic: MifareClassic, sectorId: Int): Sector
    fun getDumpFromSectors(uid: String, sector4: Sector, sector5: Sector): Dump
}
