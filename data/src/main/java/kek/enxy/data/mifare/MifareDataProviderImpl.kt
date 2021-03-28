package kek.enxy.data.mifare

import kek.enxy.data.BuildConfig
import kek.enxy.data.model.Sector

class MifareDataProviderImpl : MifareDataProvider {
    companion object {
        const val SECTOR_4 = 4
        const val SECTOR_5 = 5

        // Keys for reading/writing to Plantain Mifare Classic
        // Define them in local.properties file, they will be included in BuildConfig
        val KEY_4A = BuildConfig.KEY_4A.hexToByteArray()
        val KEY_4B = BuildConfig.KEY_4B.hexToByteArray()
        val KEY_5A = BuildConfig.KEY_5A.hexToByteArray()
        val KEY_5B = BuildConfig.KEY_5B.hexToByteArray()

        private fun String.hexToByteArray(): ByteArray {
            val len = length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(this[i], 16) shl 4)
                        + Character.digit(this[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }
    }

    override fun getSector4() = Sector(
        index = 4,
        data = arrayOf(
            byteArrayOf(112, 67, 1, 0, -113, -68, -2, -1, 112, 67, 1, 0, 0, -1, 0, -1),
            byteArrayOf(112, 67, 1, 0, -113, -68, -2, -1, 112, 67, 1, 0, 0, -1, 0, -1),
            byteArrayOf(-4, 0, -61, -7, 77, 1, -65, 15, -112, 95, 1, 1, -52, -52, -85, -118),
        )
    )

    override fun getSector5() = Sector(
        index = 5,
        data = arrayOf(
            byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -5, -74),
            byteArrayOf(0, 0, 0, 0, 0, 0, -62, -89, 8, -83, 0, 0, 0, 0, 0, 0),
            byteArrayOf(0, 0, 0, 0, 0, 0, -62, -89, 8, -83, 0, 0, 0, 0, 0, 0),
        )
    )
}
