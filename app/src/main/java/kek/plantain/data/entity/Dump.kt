package kek.plantain.data.entity

import kek.plantain.utils.extractValue
import kek.plantain.utils.slice
import kek.plantain.utils.toRubles
import java.text.SimpleDateFormat
import java.util.*

data class Dump(val tagId: String, val sector4: Sector, val sector5: Sector) {
    /**
     * Balance encoded in - sector: 4, block: 0, bytes: 0, 1, 2, 3
     */
    val balance: Rubles = sector4.data
        .slice(0, 0, 3)
        .extractValue()
        .toRubles()

    /**
     * Last payment date encoded in - sector: 4, block: 2, bytes: 2, 3, 4
     */
    val lastPaymentDate: String = extractDate(
        sector4.data
            .slice(2, 2, 4)
            .extractValue()
    )

    /**
     * Last payment amount encoded in - sector: 4, block: 2, bytes: 8, 9, 10
     */
    val lastPaymentAmount: Rubles = sector4.data
        .slice(2, 8, 10)
        .extractValue()
        .toRubles()

    /**
     * Last payed cost encoded in - sector: 5, block: 0, bytes: 6, 7
     */
    val lastPayedCost: Rubles = sector5.data
        .slice(0, 6, 7)
        .extractValue()
        .toRubles()

    /**
     * Date of last card use encoded in - sector: 5, block: 0, bytes: 0, 1, 2
     */
    val lastUsedDate: String = extractDate(
        sector5.data
            .slice(0, 0, 2)
            .extractValue()
    )

    /**
     * Count of ground travel use encoded in - sector: 5, block: 1, bytes: 1
     */
    val groundTravelCount: Int = sector5.data
        .slice(1, 1, 1)
        .extractValue()

    /**
     * Count of subway travel use encoded in - sector: 5, block: 0, bytes: 0
     */
    val subwayTravelCount: Int = sector5.data
        .slice(1, 0, 0)
        .extractValue()

    private fun extractDate(timeDiff: Int): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
        calendar[2010, 0, 1, 0, 0] = 0
        calendar.add(Calendar.MINUTE, timeDiff)
        return SimpleDateFormat("dd.MM.yy (HH:mm)", Locale.getDefault()).format(calendar.time)
    }

    override fun toString(): String {
        return "Dump(\n" +
                "tagId = $tagId,\n" +
                "sector4 = $sector4,\n" +
                "sector5 = $sector5)"
    }
}