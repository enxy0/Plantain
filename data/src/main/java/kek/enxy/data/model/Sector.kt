package kek.enxy.data.model

import android.nfc.tech.MifareClassic
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Sector(val index: Int, val data: Array<ByteArray>) : Parcelable {
    companion object {
        private const val SECTOR_SIZE = 4

        fun create(sectorId: Int) = Sector(
            index = sectorId,
            data = Array(SECTOR_SIZE) { ByteArray(MifareClassic.BLOCK_SIZE) { 0 } }
        )
    }

    fun copy() = Sector(
        index = index,
        data = data.map { array -> array.clone() }.toTypedArray()
    )

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
