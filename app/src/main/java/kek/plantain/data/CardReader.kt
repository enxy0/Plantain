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

    private const val NFC_TAG = "android.nfc.extra.TAG"
    private const val NFC_TAG_ID = "android.nfc.extra.ID"

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

    fun writeNfcTag(intent: Intent, dump: Dump): Result<Boolean, Exception> = Result.of {
        val tag: Tag = intent.getParcelableExtra(NFC_TAG)!!
        val mifareTag: MifareClassic = try {
            MifareClassic.get(tag)
        } catch (e: Exception) {
            MifareClassic.get(MifareClassicHelper.patchTag(tag))
        }
        runUsing(mifareTag) {
            dump.updateEqualBlocks()
            if (!authenticateSectorWithKeyB(4, KEY_4B))
                throw WrongSectorKeyException()
            dump.sector4.write(this, 4)
            if (!authenticateSectorWithKeyB(5, KEY_5B))
                throw WrongSectorKeyException()
            dump.sector5.write(this, 5)
            true
        }
    }
}