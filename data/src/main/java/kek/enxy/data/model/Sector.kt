package kek.enxy.data.model

data class Sector(val index: Int, val data: Array<ByteArray>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sector

        if (index != other.index) return false
        if (!data.contentDeepEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = index
        result = 31 * result + data.contentDeepHashCode()
        return result
    }
}
