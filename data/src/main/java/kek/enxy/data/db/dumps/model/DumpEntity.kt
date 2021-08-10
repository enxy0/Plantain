package kek.enxy.data.db.dumps.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kek.enxy.data.db.dumps.DumpTypeConverters
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Rubles

@Entity(tableName = "Dump")
@TypeConverters(DumpTypeConverters::class)
data class DumpEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String,
    val name: String,
    val balance: Rubles,
    val lastUseAmount: Rubles,
    val lastUseDate: AppDate,
    val lastPaymentAmount: Rubles,
    val lastPaymentDate: AppDate,
    val groundTravelTotal: Count,
    val undergroundTravelTotal: Count
)