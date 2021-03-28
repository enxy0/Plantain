package kek.enxy.data.mifare

import kek.enxy.data.model.Sector

interface MifareDataProvider {
    fun getSector4(): Sector
    fun getSector5(): Sector
}
