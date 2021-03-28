package kek.enxy.domain.write

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import kek.enxy.data.mifare.MifareDataProvider
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_4B
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_5B
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_4
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_5
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.domain.write.model.WrongSectorKeyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WriteDumpUseCaseImpl(
    private val mifareDataProvider: MifareDataProvider,
    private val readWriteDataSource: ReadWriteDataSource
) : WriteDumpUseCase {

    override fun dispatcher() = Dispatchers.IO

    override fun execute(parameters: Tag) = flow {
        var result = getWriteDataFlow(parameters).first()
        for (attempt in 1..15) {
            if (result.isSuccess) {
                emit(result)
                return@flow
            }
            result = getWriteDataFlow(parameters).first()
        }
        emit(result)
    }

    @Suppress("BlockingMethodInNonBlockingContext") // doing IO operations on IO dispatcher, everything is OK
    private fun getWriteDataFlow(parameters: Tag) = flow {
        val mifareTag: MifareClassic = try {
            MifareClassic.get(parameters)
        } catch (e: Exception) {
            val patchedNfcTag = MifareClassicPatcher.patchTag(parameters)
            MifareClassic.get(patchedNfcTag)
        }
        mifareTag.connect()
        mifareTag.use { tag ->
            if (tag.authenticateSectorWithKeyB(SECTOR_4, KEY_4B)) {
                readWriteDataSource.writeSector(tag, mifareDataProvider.getSector4())
            } else {
                throw WrongSectorKeyException("KEY_4B is not applicable to sector 4")
            }
            if (tag.authenticateSectorWithKeyB(SECTOR_5, KEY_5B)) {
                readWriteDataSource.writeSector(tag, mifareDataProvider.getSector5())
            } else {
                throw WrongSectorKeyException("KEY_5B is not applicable to sector 5")
            }
            emit(Result.success(Unit))
        }
    }
        .flowOn(dispatcher())
        .catch { e -> emit(Result.failure(e)) }
}
