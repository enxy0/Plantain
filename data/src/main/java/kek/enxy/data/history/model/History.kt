package kek.enxy.data.history.model

import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Dump

data class History(
    val timestamp: AppDate,
    val action: HistoryAction,
    val dump: Dump
)
