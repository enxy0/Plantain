package kek.plantain.data.entity

import android.nfc.tech.MifareClassic
import kek.plantain.utils.extractValue
import kek.plantain.utils.slice
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

data class Dump(val tagId: String) {
    var sector4 = Sector()
    var sector5 = Sector()

    fun readSector(mifareTag: MifareClassic, sectorId: Int) {
        val block: Int = mifareTag.sectorToBlock(sectorId)
        when (sectorId) {
            4 -> sector4.read(mifareTag, block)
            5 -> sector5.read(mifareTag, block)
            else -> throw InvalidParameterException()
        }
    }

    /**
     * Balance encoded in - sector: 4, block: 0, bytes: 0, 1, 2, 3
     */
    fun balance(): Int =
        sector4.data
            .slice(0, 0, 3)
            .extractValue()

    /**
     * Last payment date encoded in - sector: 4, block: 2, bytes: 2, 3, 4
     */
    fun lastPaymentDate(): String = extractDate(
        sector4.data
            .slice(2, 2, 4)
            .extractValue()
    )

    /**
     * Last payment amount encoded in - sector: 4, block: 2, bytes: 8, 9, 10
     */
    fun lastPaymentAmount(): Int = sector4.data
        .slice(2, 8, 10)
        .extractValue()

    /**
     * Last payed cost encoded in - sector: 5, block: 0, bytes: 6, 7
     */
    fun lastPayedCost(): Int = sector5.data
        .slice(0, 6, 7)
        .extractValue()

    /**
     * Date of last card use encoded in - sector: 5, block: 0, bytes: 0, 1, 2
     */
    fun lastUsedDate(): String = extractDate(
        sector5.data
            .slice(0, 0, 2)
            .extractValue()
    )

    /**
     * Count of ground travel use encoded in - sector: 5, block: 1, bytes: 1
     */
    fun groundTravelCount(): Int = sector5.data
        .slice(1, 1, 1)
        .extractValue()

    /**
     * Count of subway travel use encoded in - sector: 5, block: 0, bytes: 0
     */
    fun subwayTravelCount(): Int = sector5.data
        .slice(1, 0, 0)
        .extractValue()

    private fun extractDate(timeDiff: Int): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
        calendar[2010, 0, 1, 0, 0] = 0
        calendar.add(Calendar.MINUTE, timeDiff)
        return SimpleDateFormat("dd.MM.YYYY HH:mm", Locale.getDefault()).format(calendar.time)
    }
}