package kek.plantain.data.entity

import kek.plantain.data.delegate.Count
import kek.plantain.data.delegate.Date
import kek.plantain.data.delegate.Money

data class Dump(val tagId: String, val sector4: Sector, val sector5: Sector) {
    /**
     * Balance encoded in - sector: 4, block: 0, bytes: 0, 1, 2, 3
     */
    val balance: Rubles by Money(sector4, 0, 0..4)

    /**
     * Last payment date encoded in - sector: 4, block: 2, bytes: 2, 3, 4
     */
    val lastPaymentDate: String by Date(sector4, 2, 2..5)

    /**
     * Last payment amount encoded in - sector: 4, block: 2, bytes: 8, 9, 10
     */
    val lastPaymentAmount: Rubles by Money(sector4, 2, 8..11)

    /**
     * Last payed cost encoded in - sector: 5, block: 0, bytes: 6, 7
     */
    val lastPayedCost: Rubles by Money(sector5, 0, 6..8)

    /**
     * Date of last card use encoded in - sector: 5, block: 0, bytes: 0, 1, 2
     */
    val lastUsedDate: String by Date(sector5, 0, 0..3)

    /**
     * Count of ground travel use encoded in - sector: 5, block: 1, bytes: 1
     */
    val groundTravelCount: Int by Count(sector5, 1, 1..2)

    /**
     * Count of subway travel use encoded in - sector: 5, block: 0, bytes: 0
     */
    val subwayTravelCount: Int by Count(sector5, 1, 0..1)

    override fun toString(): String {
        return "Dump(\n" +
                "tagId = $tagId,\n" +
                "sector4 = $sector4,\n" +
                "sector5 = $sector5)"
    }
}