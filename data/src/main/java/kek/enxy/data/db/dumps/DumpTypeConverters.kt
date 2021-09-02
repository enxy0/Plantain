package kek.enxy.data.db.dumps

import androidx.room.TypeConverter
import kek.enxy.data.model.Sector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class DumpTypeConverters {
    @TypeConverter
    fun toSector(raw: String?): Sector? = raw?.let { Json.decodeFromString<Sector>(it) }

    @TypeConverter
    fun fromSector(sector: Sector?): String = Json.encodeToString(sector)
}
