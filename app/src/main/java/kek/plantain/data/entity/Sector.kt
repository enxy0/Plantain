package kek.plantain.data.entity

data class Sector(val sectorId: Int, val data: Array<ByteArray>) {

    override fun toString(): String {
        val indention = "    "
        return data.joinToString(separator = "\n", prefix = "(\n", postfix = "\n)") {
            indention + it.joinToString(prefix = "(", postfix = ")")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sector

        if (sectorId != other.sectorId) return false
        if (!data.contentDeepEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sectorId
        result = 31 * result + data.contentDeepHashCode()
        return result
    }
}