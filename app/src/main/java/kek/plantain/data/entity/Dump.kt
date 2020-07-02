package kek.plantain.data.entity

import android.nfc.tech.MifareClassic
import kek.plantain.utils.extractNumber
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

data class Dump(val tagName: String) {
    var sector4 = Sector()
    var sector5 = Sector()

    companion object {
        const val RADIX = 16
    }

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
    fun balance(): Int = sector4
        .slice(0, 0, 3)
        .extractNumber(RADIX)

    /**
     * Last payment date encoded in - sector: 4, block: 2, bytes: 2, 3, 4
     */
    fun lastPaymentDate(): String = parseDate(
        sector4
            .slice(2, 2, 4)
            .extractNumber(16)
    )

    /**
     * Last payment amount encoded in - sector: 4, block: 2, bytes: 8, 9, 10
     */
    fun lastPaymentAmount(): Int = sector4
        .slice(2, 8, 10)
        .extractNumber(RADIX)

    /**
     * Last payed cost encoded in - sector: 5, block: 0, bytes: 6, 7
     */
    fun lastPayedCost(): Int = sector5
        .slice(0, 6, 7)
        .extractNumber(RADIX)

    /**
     * Date of last card use encoded in - sector: 5, block: 0, bytes: 0, 1, 2
     */
    fun lastUsedDate(): String = parseDate(
        sector5
            .slice(0, 0, 2)
            .extractNumber(16)
    )

    /**
     * Count of ground travel use encoded in - sector: 5, block: 1, bytes: 1
     */
    fun groundTravelCount(): Int = sector5
        .slice(1, 1, 1)
        .extractNumber(RADIX)

    /**
     * Count of subway travel use encoded in - sector: 5, block: 0, bytes: 0
     */
    fun subwayTravelCount(): Int = sector5
        .slice(1, 0, 0)
        .extractNumber(RADIX)

    private fun parseDate(timeDiff: Int): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
        calendar[2010, 0, 1, 0, 0] = 0
        calendar.add(Calendar.MINUTE, timeDiff)
        return SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.time)
    }
}