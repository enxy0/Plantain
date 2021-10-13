package kek.enxy.domain.history.model

import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Rubles

class CardProfit(
    val savedTotal: Rubles,
    val writesTotal: Int,
    val readsTotal: Int,
    val toDate: AppDate,
    val fromDate: AppDate
)
