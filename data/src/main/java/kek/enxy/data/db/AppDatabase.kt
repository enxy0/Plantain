package kek.enxy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kek.enxy.data.db.dumps.DumpsDao
import kek.enxy.data.db.dumps.model.DumpEntity


@Database(
    entities = [
        DumpEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dumpsDao(): DumpsDao
}
