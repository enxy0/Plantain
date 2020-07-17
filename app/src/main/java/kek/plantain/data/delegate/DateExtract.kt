package kek.plantain.data.delegate

import kek.plantain.data.entity.Date
import kek.plantain.data.entity.Sector
import kek.plantain.utils.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DateExtract(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, Date> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Date {
        return sector.data
            .slice(block, range)
            .extractValue()
            .toDate()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Date) {
        sector.data[block].writeValue(value.raw, range)
    }
}