package kek.enxy.data.db.dumps

import androidx.room.*
import kek.enxy.data.db.dumps.model.DumpEntity
import kek.enxy.data.readwrite.model.Dump
import kotlinx.coroutines.flow.Flow

@Dao
interface DumpsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDump(dump: DumpEntity)

    @Query("SELECT * FROM Dump")
    fun getDumpsFlow(): Flow<List<DumpEntity>>

    @Query("DELETE FROM Dump WHERE id = :id")
    fun deleteDump(id: Int)
}
