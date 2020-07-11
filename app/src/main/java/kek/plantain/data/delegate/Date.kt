package kek.plantain.data.delegate

import kek.plantain.data.entity.Sector
import kek.plantain.utils.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Date(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sector.data
            .slice(block, range)
            .extractDate()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        TODO("Not implemented yet")
    }
}