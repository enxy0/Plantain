package kek.enxy.data.db.history.model

import androidx.room.*
import kek.enxy.data.db.dumps.model.DumpEntity
import kek.enxy.data.db.history.HistoryConverters
import kek.enxy.data.history.model.HistoryAction
import kek.enxy.data.readwrite.model.AppDate

@Entity(tableName = "History")
@TypeConverters(HistoryConverters::class)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: AppDate,
    val action: HistoryAction,
    @Embedded(prefix = "dump_")
    val dump: DumpEntity
)
