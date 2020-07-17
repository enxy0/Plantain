package kek.plantain.data.delegate

import kek.plantain.data.entity.Rubles
import kek.plantain.data.entity.Sector
import kek.plantain.utils.extensions.extractValue
import kek.plantain.utils.extensions.writeValue
import kek.plantain.utils.slice
import kek.plantain.utils.toRubles
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RublesExtract(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, Rubles> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Rubles {
        return sector.data
            .slice(block, range)
            .extractValue()
            .toRubles()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Rubles) {
        sector.data[block].writeValue(value.raw, range)
    }
}