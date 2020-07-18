package kek.plantain.data.entity

data class Rubles(val raw: Int) {
    private val rubles: Int = (raw / 100)

    companion object {
        fun isValid(input: String): Boolean = input.matches("^\\d+$".toRegex())
    }

    override fun toString(): String = rubles.toString()
}

private fun String.toRubles(): Rubles {
    return (toInt() * 100).toRubles()
}

fun String.toRublesOr(default: Rubles): Rubles =
    if (Rubles.isValid(this)) toRubles() else default

fun Int.toRubles() = Rubles(this)