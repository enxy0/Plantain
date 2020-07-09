package kek.plantain.data

import android.content.Intent
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.data.entity.Sector
import kek.plantain.utils.WrongSectorKeyException
import kek.plantain.utils.runUsing
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
        val mifareTag: MifareClassic = try {
            MifareClassic.get(tag)
        } catch (e: Exception) {
            MifareClassic.get(MifareClassicHelper.patchTag(tag))
        }
        runUsing(mifareTag) {
            if (!authenticateSectorWithKeyA(4, KEY_4A))
                throw WrongSectorKeyException()
            val sector4 = Sector().apply { read(mifareTag, 4) }
            if (!authenticateSectorWithKeyA(5, KEY_5A))
                throw WrongSectorKeyException()
            val sector5 = Sector().apply { read(mifareTag, 5) }
            Dump(tagId.toHex(), sector4, sector5).also {
                Log.d(TAG, "readNfcTag: dump=$it")
            }
        }
    }
}