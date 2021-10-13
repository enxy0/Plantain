package kek.enxy.data.db.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kek.enxy.data.db.history.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY timestamp DESC")
    fun getHistoryFlow(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM History WHERE dump_uid = :uid ORDER BY timestamp DESC")
    fun getHistoryByUidFlow(uid: String): Flow<List<HistoryEntity>>

    @Insert
    fun addHistory(history: HistoryEntity)
}
