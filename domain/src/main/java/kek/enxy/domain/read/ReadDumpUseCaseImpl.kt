package kek.enxy.domain.read

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.domain.write.MifareClassicPatcher
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_4A
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_5A
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_4
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_5
import kek.enxy.domain.write.model.WrongSectorKeyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReadDumpUseCaseImpl(
    private val readWriteDataSource: ReadWriteDataSource
) : ReadDumpUseCase {

    override fun dispatcher() = Dispatchers.IO

    override fun execute(parameters: Tag) = flow {
        var result = getReadDataFlow(parameters).first()
        for (attempt in 1..15) {
            if (result.isSuccess) {
                emit(result)
                return@flow
            }
            result = getReadDataFlow(parameters).first()
        }
        emit(result)
    }

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
                throw WrongSectorKeyException("KEY_4A is not applicable to sector 4")
            }
            val sector5 = if (tag.authenticateSectorWithKeyA(SECTOR_5, KEY_5A)) {
                readWriteDataSource.getSector(tag, SECTOR_5)
            } else {
                throw WrongSectorKeyException("KEY_5A is not applicable to sector 5")
            }
            val dump = readWriteDataSource.getDumpFromSectors(sector4, sector5)
            emit(Result.success(dump))
        }
    }
        .flowOn(dispatcher())
        .catch { e -> emit(Result.failure(e)) }
}