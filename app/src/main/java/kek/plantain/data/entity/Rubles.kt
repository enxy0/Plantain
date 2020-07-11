package kek.plantain.data.entity

class Rubles(val raw: Int) {
    val rubles: Int = raw / 100
    val pennies: Int = raw % 100

    override fun toString(): String {
        return "$rubles,$pennies₽"
    }
}