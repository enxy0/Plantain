package kek.enxy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kek.enxy.data.db.dumps.DumpsDao
import kek.enxy.data.db.dumps.model.DumpEntity
import kek.enxy.data.db.history.HistoryDao
import kek.enxy.data.db.history.model.HistoryEntity

@Database(
    entities = [
        DumpEntity::class,
        HistoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dumpsDao(): DumpsDao
    abstract fun historyDao(): HistoryDao
}
