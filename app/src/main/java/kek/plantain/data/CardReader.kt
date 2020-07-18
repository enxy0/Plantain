package kek.plantain.data

import android.content.Intent
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.utils.WrongSectorKeyException
import kek.plantain.utils.extensions.*

object CardReader {
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
        val mifareTag: MifareClassic = tag.getMifareTagOr {
            MifareClassicHelper.patchTag(it)
        }
        mifareTag.using {
            if (!authenticateSectorWithKeyA(4, KEY_4A))
                throw WrongSectorKeyException()
            val sector4 = getSector(4)
            if (!authenticateSectorWithKeyA(5, KEY_5A))
                throw WrongSectorKeyException()
            val sector5 = getSector(5)
            Dump(tagId.toHex(), sector4, sector5).also {
                Log.d("CardReader", "readNfcTag: dump=$it")
            }
        }
    }

    fun writeNfcTag(intent: Intent, dump: Dump): Result<Boolean, Exception> = Result.of {
        val tag: Tag = intent.getParcelableExtra(NFC_TAG)!!
        val mifareTag: MifareClassic = tag.getMifareTagOr {
            MifareClassicHelper.patchTag(it)
        }
        dump.updateEqualBlocks()
        mifareTag.using {
            if (!authenticateSectorWithKeyB(4, KEY_4B))
                throw WrongSectorKeyException()
            writeFrom(dump.sector4)
            if (!authenticateSectorWithKeyB(5, KEY_5B))
                throw WrongSectorKeyException()
            writeFrom(dump.sector5)
            true
        }
    }
}