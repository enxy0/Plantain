package kek.enxy.plantwriter.presentation.common.extensions

fun String?.getDigitsOrNull() = this?.filter { it.isDigit() }
