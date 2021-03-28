package kek.enxy.data.readwrite.model

data class Rubles(
    private val raw: Int
) {
    val value: Int = raw / 100

    override fun toString() = (raw / 100).toString()
}
