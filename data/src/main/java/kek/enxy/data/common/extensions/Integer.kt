package kek.enxy.data.common.extensions

import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Rubles

fun Int.toRubles() = Rubles(this)

fun Int.toAppDate() = AppDate(this)

fun Int.toCount() = Count(this)
