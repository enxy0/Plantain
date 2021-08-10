package kek.enxy.data.db.dumps

import androidx.room.TypeConverter
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Rubles

class DumpTypeConverters {
    @TypeConverter
    fun toRubles(raw: Int?): Rubles? = raw?.let { Rubles(it) }

    @TypeConverter
    fun fromRubles(rubles: Rubles?): Int? = rubles?.raw

    @TypeConverter
    fun toAppDate(raw: Int?): AppDate? = raw?.let { AppDate(raw) }

    @TypeConverter
    fun fromAppDate(appDate: AppDate?): Int? = appDate?.raw

    @TypeConverter
    fun toCount(raw: Int?): Count? = raw?.let { Count(raw) }

    @TypeConverter
    fun fromCount(count: Count?): Int? = count?.raw
}
