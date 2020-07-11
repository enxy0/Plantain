package kek.plantain.data.delegate

import kek.plantain.data.entity.Sector
import kek.plantain.utils.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Count(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, Int> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return sector.data
            .slice(block, range)
            .extractValue()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        value.writeValue(sector.data[block])
    }
}
