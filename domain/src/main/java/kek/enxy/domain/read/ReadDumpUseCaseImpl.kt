package kek.enxy.domain.read

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_4A
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_5A
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_4
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_5
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.domain.write.MifareClassicPatcher
import kek.enxy.domain.write.model.WrongSectorKeyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class ReadDumpUseCaseImpl(
    private val readWriteDataSource: ReadWriteDataSource
) : ReadDumpUseCase {

    override fun dispatcher() = Dispatchers.IO

    override fun execute(parameters: Tag) = getReadDataFlow(parameters).retry(retries = 15)

    @Suppress("BlockingMethodInNonBlockingContext") // doing IO operations on IO dispatcher, everything is OK
    private fun getReadDataFlow(parameters: Tag) = flow {
        val mifareTag: MifareClassic = try {
            MifareClassic.get(parameters)
        } catch (e: Exception) {
            val patchedNfcTag = MifareClassicPatcher.patchTag(parameters)
            MifareClassic.get(patchedNfcTag)
        }
        mifareTag.connect()
        mifareTag.use { tag ->
            val sector4 = if (tag.authenticateSectorWithKeyA(SECTOR_4, KEY_4A)) {
                readWriteDataSource.getSector(tag, SECTOR_4)
            } else {
                throw WrongSectorKeyException("KEY_4A")
            }
            val sector5 = if (tag.authenticateSectorWithKeyA(SECTOR_5, KEY_5A)) {
                readWriteDataSource.getSector(tag, SECTOR_5)
            } else {
                throw WrongSectorKeyException("KEY_5A")
            }
            val dump = readWriteDataSource.getDumpFromSectors(sector4, sector5)
            emit(Result.success(dump))
        }
    }
}