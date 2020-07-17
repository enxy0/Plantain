package kek.plantain.data.entity

import android.util.Log
import kek.plantain.data.delegate.CountExtract
import kek.plantain.data.delegate.DateExtract
import kek.plantain.data.delegate.RublesExtract
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.EditScreenType.*
import kek.plantain.utils.toCountOr
import kek.plantain.utils.toDateOr
import kek.plantain.utils.toRublesOr

data class Dump(val tagId: String, val sector4: Sector, val sector5: Sector) {
    /**
     * Balance encoded in - sector: 4, block: 0, bytes: 0, 1, 2, 3
     */
    var balance: Rubles by RublesExtract(sector4, 0, 0..3)

    /**
     * Last payment date encoded in - sector: 4, block: 2, bytes: 2, 3, 4
     */
    var lastPaymentDate: Date by DateExtract(sector4, 2, 2..4)

    /**
     * Last payment amount encoded in - sector: 4, block: 2, bytes: 8, 9, 10
     */
    var lastPaymentMoney: Rubles by RublesExtract(sector4, 2, 8..10)

    /**
     * Last payed cost encoded in - sector: 5, block: 0, bytes: 6, 7
     */
    var lastPayedMoney: Rubles by RublesExtract(sector5, 0, 6..7)

    /**
     * Date of last card use encoded in - sector: 5, block: 0, bytes: 0, 1, 2
     */
    var lastUsedDate: Date by DateExtract(sector5, 0, 0..2)

    /**
     * Count of ground travel use encoded in - sector: 5, block: 1, bytes: 1
     */
    var groundTravelCount: Count by CountExtract(sector5, 1, 1..1)

    /**
     * Count of subway travel use encoded in - sector: 5, block: 0, bytes: 0
     */
    var subwayTravelCount: Count by CountExtract(sector5, 1, 0..0)

    fun overwrite(type: EditScreenType, input: String) {
        when (type) {
            BALANCE ->
                balance = input.toRublesOr(balance)
            LAST_PAYED_COST ->
                lastPayedMoney = input.toRublesOr(lastPayedMoney)
            LAST_PAYMENT_AMOUNT ->
                lastPaymentMoney = input.toRublesOr(lastPaymentMoney)
            LAST_PAYMENT_DATE ->
                lastPaymentDate = input.toDateOr(lastPaymentDate)
            LAST_USED_DATE ->
                lastUsedDate = input.toDateOr(lastUsedDate)
            GROUND_TRAVEL_COUNT ->
                groundTravelCount = input.toCountOr(groundTravelCount)
            SUBWAY_TRAVEL_COUNT ->
                subwayTravelCount = input.toCountOr(groundTravelCount)
        }
        Log.d("Dump", "overwrite: dump=${toString()}")
    }

    fun updateEqualBlocks() {
        sector4.data[1] = sector4.data[0]
        sector5.data[2] = sector5.data[1]
    }

    override fun toString(): String {
        return "Dump(\n" +
                "tagId = $tagId,\n" +
                "sector4 = $sector4,\n" +
                "sector5 = $sector5)"
    }
}