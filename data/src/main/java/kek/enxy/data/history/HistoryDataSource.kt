package kek.enxy.data.history

import kek.enxy.data.history.model.History
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Dump
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistoryFlow(): Flow<List<History>>
    fun getHistoryByUidFlow(uid: String): Flow<List<History>>
    fun logRead(timestamp: AppDate, dump: Dump)
    fun logWrite(timestamp: AppDate, dump: Dump)
}
