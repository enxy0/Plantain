package kek.enxy.data.db.history

import androidx.room.TypeConverter
import kek.enxy.data.history.model.HistoryAction
import kek.enxy.data.readwrite.model.AppDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class HistoryConverters {
    @TypeConverter
    fun toAction(raw: String?): HistoryAction? =
        raw?.let { Json.decodeFromString(HistoryAction.serializer(), it) }

    @TypeConverter
    fun fromAction(action: HistoryAction?): String? =
        action?.let { Json.encodeToString(HistoryAction.serializer(), it) }

    @TypeConverter
    fun toAppDate(raw: Int?): AppDate? = raw?.let { AppDate(it) }

    @TypeConverter
    fun fromAppDate(date: AppDate): Int = date.raw
}
