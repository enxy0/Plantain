package kek.enxy.data.dumps

import kek.enxy.data.db.dumps.DumpsDao
import kek.enxy.data.readwrite.model.Dump
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DumpsDataSourceImpl(
    private val dumpsDao: DumpsDao
) : DumpsDataSource {
    override fun getDumpsFlow(): Flow<List<Dump>> = dumpsDao.getDumpsFlow().map { dumps ->
        dumps.map { dump -> dump.toDomain() }
    }

    override fun saveDump(dump: Dump) {
        dumpsDao.insertDump(dump.toEntity())
    }

    override fun removeDump(dump: Dump) {
        dumpsDao.deleteDump(dump.id)
    }

    override fun getLastDumpId(): Int {
        return dumpsDao.getLastDumpId() ?: 0
    }
}
