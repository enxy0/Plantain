package kek.plantain.data.delegate

import kek.plantain.data.entity.Rubles
import kek.plantain.data.entity.Sector
import kek.plantain.data.entity.toRubles
import kek.plantain.utils.extensions.extractValue
import kek.plantain.utils.extensions.writeValue
import kek.plantain.utils.extensions.writeValueBlock
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RublesExtract(sector: Sector, block: Int, range: IntRange) :
    ReadWriteSector(sector, block, range), ReadWriteProperty<Any?, Rubles> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Rubles {
        return sector.data[block]
            .copyOfRange(range.first, range.last + 1)
            .extractValue()
            .toRubles()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Rubles) {
        // If it contains balance for Plantain card
        if (sector.sectorId == 4 && block == 0) {
            // Creating value block
            sector.data[block].writeValueBlock(value.raw)
        } else {
            sector.data[block].writeValue(value.raw, range)
        }
    }
}