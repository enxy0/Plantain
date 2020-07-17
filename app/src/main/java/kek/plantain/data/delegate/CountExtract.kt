package kek.plantain.data.delegate

import kek.plantain.data.entity.Count
import kek.plantain.data.entity.Sector
import kek.plantain.utils.extensions.extractValue
import kek.plantain.utils.extensions.writeValue
import kek.plantain.utils.slice
import kek.plantain.utils.toCount
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CountExtract(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, Count> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Count {
        return sector.data
            .slice(block, range)
            .extractValue()
            .toCount()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Count) {
        sector.data[block].writeValue(value.raw, range)
    }
}