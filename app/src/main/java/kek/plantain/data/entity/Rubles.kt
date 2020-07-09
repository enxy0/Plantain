package kek.plantain.data.entity

class Rubles(amount: Int) {
    val rubles: Int = amount / 100
    val pennies: Int = amount % 100

    override fun toString(): String {
        return "$rubles,$pennies₽"
    }
}