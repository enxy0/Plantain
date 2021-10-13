package kek.enxy.data.db.dumps

import androidx.room.TypeConverter
import kek.enxy.data.model.Sector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class DumpTypeConverters {
    @TypeConverter
    fun toSector(raw: String?): Sector? =
        raw?.let { Json.decodeFromString(Sector.serializer(), it) }

    @TypeConverter
    fun fromSector(sector: Sector?): String? =
        sector?.let { Json.encodeToString(Sector.serializer(), it) }
}
