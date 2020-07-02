package kek.plantain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.data.entity.Sector

@OptIn(ExperimentalUnsignedTypes::class)
fun getFakeDump(): Dump = Dump(
    tagName = "4445772F66780"
).apply {
    sector4 = Sector().apply {
        data = arrayOf(
            byteArrayOf(-60, 109, 0, 0, 59, -110, -1, -1, -60, 109, 0, 0, 0, -1, 0, -1).toUByteArray(),
            byteArrayOf(-60, 109, 0, 0, 59, -110, -1, -1, -60, 109, 0, 0, 0, -1, 0, -1).toUByteArray(),
            byteArrayOf(-4, 0, -108, -19, 83, 1, 126, 8, 80, -61, 0, 1, -23, -8, -50, 19).toUByteArray(),
            byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).toUByteArray()
        )
    }
    sector5 = Sector().apply {
        data = arrayOf(
            byteArrayOf(12, 49, 84, 1, 0, 0, -28, 12, 0, 0, 0, 0, 0, 0, -11, 110).toUByteArray(),
            byteArrayOf(2, 3, 12, 49, 84, 0, 109, 65, -44, 102, 0, 0, 0, 0, 0, 0).toUByteArray(),
            byteArrayOf(2, 3, 12, 49, 84, 0, 109, 65, -44, 102, 0, 0, 0, 0, 0, 0).toUByteArray(),
            byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).toUByteArray()
        )
    }
}

fun getFakeLiveDataDump(): LiveData<Result<Dump, Exception>> = liveData {
    emit(Result.of(getFakeDump()))
}