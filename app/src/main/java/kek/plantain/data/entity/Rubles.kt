package kek.plantain.data.entity

data class Rubles(val raw: Int) {
    private val rubles: Int = (raw / 100)

    companion object {
        fun isValid(input: String): Boolean = input.matches("^\\d+$".toRegex())
    }

    override fun toString(): String = rubles.toString()
}