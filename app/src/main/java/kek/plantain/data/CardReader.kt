package kek.plantain.data

import android.content.Intent
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.utils.WrongSectorKeyThrowable
import kek.plantain.utils.pretty
import kek.plantain.utils.toHex
import java.io.IOException

object CardReader {
    private const val TAG = "CardReader"

    // SECTOR: 4, KEY: A
    private val KEY_4A = byteArrayOf(0, 0, 0, 0, 0, 0)

    // SECTOR: 4, KEY: B
    private val KEY_4B = byteArrayOf(0, 0, 0, 0, 0, 0)

    // SECTOR: 5, KEY: A
    private val KEY_5A = byteArrayOf(0, 0, 0, 0, 0, 0)

    // SECTOR: 5, KEY: B
    private val KEY_5B = byteArrayOf(0, 0, 0, 0, 0, 0)


    const val NFC_TAG = "android.nfc.extra.TAG"
    const val NFC_TAG_ID = "android.nfc.extra.ID"

    @Throws(IOException::class)
    fun readNfcTag(intent: Intent): Result<Dump, Exception> = Result.of {
        val tag: Tag = intent.getParcelableExtra(NFC_TAG)!!
        val tagId: ByteArray = intent.getByteArrayExtra(NFC_TAG_ID)!!
        val mifareTag: MifareClassic = MifareClassic.get(tag)
        runUsing(mifareTag) {
            val dump = Dump(tagId.toHex())
            if (!authenticateSectorWithKeyA(4, KEY_4A))
                throw WrongSectorKeyThrowable("Sector 4, Key 4A")
            dump.readSector(mifareTag, 4)
            if (!authenticateSectorWithKeyA(5, KEY_5A))
                throw WrongSectorKeyThrowable("Sector 5, Key 5A")
            dump.readSector(mifareTag, 5)
            Log.d(TAG, "readNfcTag: dump=$dump")
            Log.d(TAG, "readNfcTag: dump.sector4=${dump.sector4.data.pretty()}")
            Log.d(TAG, "readNfcTag: dump.sector5=${dump.sector5.data.pretty()}")
            dump
        }
    }
}

/**
 * Connects to the given [tag], invokes [fn] in context of [tag], closes [tag] and returns the result.
 */
inline fun <T> runUsing(tag: MifareClassic, fn: MifareClassic.() -> T): T {
    tag.connect()
    val result = fn.invoke(tag)
    tag.close()
    return result
}