package kek.plantain.data.delegate

import kek.plantain.data.entity.Sector

open class ReadWriteSector(val sector: Sector, val block: Int, val range: IntRange)