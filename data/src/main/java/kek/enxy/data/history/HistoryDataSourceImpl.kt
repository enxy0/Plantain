package kek.enxy.data.history

import kek.enxy.data.db.history.HistoryDao
import kek.enxy.data.db.history.model.HistoryEntity
import kek.enxy.data.dumps.toEntity
import kek.enxy.data.history.model.History
import kek.enxy.data.history.model.HistoryAction
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Dump
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryDataSourceImpl(
    private val historyDao: HistoryDao
) : HistoryDataSource {
    override fun getHistoryFlow(): Flow<List<History>> = historyDao
        .getHistoryFlow()
        .map { histories -> histories.map { entity -> entity.toDomain() } }

    override fun getHistoryByUidFlow(uid: String): Flow<List<History>> = historyDao
        .getHistoryByUidFlow(uid)
        .map { histories -> histories.map { entity -> entity.toDomain() } }

    override fun logRead(timestamp: AppDate, dump: Dump) {
        historyDao.addHistory(
            HistoryEntity(
                timestamp = timestamp,
                action = HistoryAction.READ,
                dump = dump.toEntity()
            )
        )
    }

    override fun logWrite(timestamp: AppDate, dump: Dump) {
        historyDao.addHistory(
            HistoryEntity(
                timestamp = timestamp,
                action = HistoryAction.WRITE,
                dump = dump.toEntity()
            )
        )
    }
}
