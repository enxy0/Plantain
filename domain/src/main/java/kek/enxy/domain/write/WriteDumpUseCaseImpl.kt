package kek.enxy.domain.write

import android.nfc.tech.MifareClassic
import kek.enxy.data.history.HistoryDataSource
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_4B
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.KEY_5B
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_4
import kek.enxy.data.mifare.MifareDataProviderImpl.Companion.SECTOR_5
import kek.enxy.data.readwrite.ReadWriteDataSource
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.domain.write.model.WriteDumpParams
import kek.enxy.domain.write.model.WrongSectorKeyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class WriteDumpUseCaseImpl(
    private val readWriteDataSource: ReadWriteDataSource,
    private val historyDataSource: HistoryDataSource
) : WriteDumpUseCase {

    override fun execute(parameter: WriteDumpParams) = getWriteDataFlow(parameter).retry(retries = 15)

    @Suppress("BlockingMethodInNonBlockingContext") // doing IO operations on IO dispatcher, everything is OK
    private fun getWriteDataFlow(parameter: WriteDumpParams) = flow {
        val mifare: MifareClassic = try {
            MifareClassic.get(parameter.tag)
        } catch (e: Exception) {
            val patchedNfcTag = MifareClassicPatcher.patchTag(parameter.tag)
            MifareClassic.get(patchedNfcTag)
        }
        mifare.connect()
        mifare.use { tag ->
            if (tag.authenticateSectorWithKeyB(SECTOR_4, KEY_4B)) {
                readWriteDataSource.writeSector(tag, parameter.dump.sector4)
            } else {
                throw WrongSectorKeyException("KEY_4B")
            }
            if (tag.authenticateSectorWithKeyB(SECTOR_5, KEY_5B)) {
                readWriteDataSource.writeSector(tag, parameter.dump.sector5)
            } else {
                throw WrongSectorKeyException("KEY_5B")
            }
//            historyDataSource.logWrite(AppDate.now(), parameter.dump.copy(uid = parameter.tagId))
            emit(Result.success(Unit))
        }
    }
}
