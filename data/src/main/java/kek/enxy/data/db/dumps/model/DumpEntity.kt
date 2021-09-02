package kek.enxy.data.db.dumps.model

import androidx.room.*
import kek.enxy.data.db.dumps.DumpTypeConverters
import kek.enxy.data.model.Sector

@Entity(tableName = "Dump", indices = [Index(value = ["name"], unique = true)])
@TypeConverters(DumpTypeConverters::class)
data class DumpEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String,
    @ColumnInfo(name = "name")
    val name: String,
    val sector4: Sector,
    val sector5: Sector
)
