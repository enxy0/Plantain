package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kek.enxy.data.common.extensions.toAppDate
import kek.enxy.data.common.extensions.toCount
import kek.enxy.data.common.extensions.toRubles
import kek.enxy.data.model.Sector
import kotlinx.parcelize.Parcelize
import java.nio.ByteBuffer
import kotlin.experimental.inv

@Parcelize
data class Dump(
    val uid: String,
    val sector4: Sector,
    val sector5: Sector,
    val id: Int = 0,
    val name: String = ""
) : Parcelable {

    companion object {
        fun empty() = Dump(
            uid = "",
            sector4 = Sector.create(4),
            sector5 = Sector.create(5)
        )
    }

    // Баланс
    var balance: Rubles
        get() = sector4.data[0]
            .copyOfRange(0..3)
            .extractValue()
            .toRubles()
        set(value) {
            sector4.data[0].writeValueBlock(value.raw)
        }

    // Сумма за последнее использование
    var lastUseAmount: Rubles
        get() = sector5.data[0]
            .copyOfRange(6..7)
            .extractValue()
            .toRubles()
        set(value) {
            sector5.data[0].writeValue(value.raw, 6..7)
        }

    // Дата последнего использования
    var lastUseDate: AppDate
        get() = sector5.data[0]
            .copyOfRange(0..2)
            .extractValue()
            .toAppDate()
        set(value) {
            sector5.data[0].writeValue(value.raw, 0..2)
        }

    // Сумма последнего пополнения
    var lastPaymentAmount: Rubles
        get() = sector4.data[2]
            .copyOfRange(8..10)
            .extractValue()
            .toRubles()
        set(value) {
            sector4.data[2].writeValue(value.raw, 8..10)
        }

    // Дата последнего пополнения
    var lastPaymentDate: AppDate
        get() = sector4.data[2]
            .copyOfRange(2..4)
            .extractValue()
            .toAppDate()
        set(value) {
            sector4.data[2].writeValue(value.raw, 2..4)
        }

    // Количество поездок на наземном транспорте
    var groundTravelTotal: Count
        get() = sector5.data[1]
            .copyOfRange(1..1)
            .extractValue()
            .toCount()
        set(value) {
            sector5.data[1].writeValue(value.raw, 1..1)
        }

    // Количество поездок в метро
    var undergroundTravelTotal: Count
        get() = sector5.data[1]
            .copyOfRange(0..0)
            .extractValue()
            .toCount()
        set(value) {
            sector5.data[1].writeValue(value.raw, 0..0)
        }

    fun copy() = Dump(
        uid = uid,
        name = name,
        id = id,
        sector4 = sector4.copy(),
        sector5 = sector5.copy()
    )

    /**
     * Extract [Int] value from the given [ByteArray]
     * @receiver ByteArray
     * @return Int
     */
    private fun ByteArray.extractValue(): Int = foldIndexed(0) { index, acc, byte ->
        val shift = if (index == 0) 0 else 2 shl index + 1
        acc + ((byte.toInt() and 0xFF) shl shift)
    }

    /**
     * Write [Int] into the [ByteArray]
     */
    private fun ByteArray.writeValue(value: Int, range: IntRange) {
        val bytes = ByteBuffer.allocate(4)
            .putInt(Integer.reverseBytes(value))
            .array()
        var j = 0
        for (i in range) {
            this[i] = bytes[j++]
        }
    }

    /**
     * Value blocks are stored in a fixed data format:
     *
     * 0-3: Value, 4-7: Inverted Value, 8 - 11: Value, 12: address, 13: inverted address, 14: address, 15: inverted address
     *
     * Important: balance of the Plantain card is stored as a value block.
     * @param value 4-byte signed value
     * @see <a href="https://www.nxp.com/docs/en/data-sheet/MF1S50YYX_V1.pdf">Mifare Classic Documentation (page 9, 8.6.2.1 Value blocks)</a>
     */
    private fun ByteArray.writeValueBlock(value: Int) {
        val bytes = ByteBuffer.allocate(4)
            .putInt(Integer.reverseBytes(value))
            .array()
            .copyOfRange(0, 4) // value must fit in 4 bytes

        val invertedBytes = bytes.map { it.inv() }.toByteArray()

        val address = byteArrayOf(0, -1, 0, -1) // 00 FF 00 FF
        val block = bytes + invertedBytes + bytes + address

        for (i in block.indices) {
            this[i] = block[i]
        }
    }

    private fun ByteArray.copyOfRange(range: IntRange) = copyOfRange(range.first, range.last + 1)
}
