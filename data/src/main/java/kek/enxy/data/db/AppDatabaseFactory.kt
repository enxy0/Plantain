package kek.enxy.data.db

import android.content.Context
import androidx.room.Room

object AppDatabaseFactory {
    private const val dbName = "plantain-db"

    fun create(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .createFromAsset("database/$dbName.db")
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
}
