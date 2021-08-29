package kek.enxy.data.dumps

import kek.enxy.data.readwrite.model.Dump
import kotlinx.coroutines.flow.Flow

interface DumpsDataSource {
    fun getDumpsFlow(): Flow<List<Dump>>
    fun saveDump(dump: Dump)
    fun removeDump(dump: Dump)
    fun getLastDumpId(): Int
}
