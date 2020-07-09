package kek.plantain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.data.entity.Sector

fun getFakeDump(): Dump = Dump(
    tagId = "4445772F66780",
    sector4 = Sector().apply {
        data = arrayOf(
            byteArrayOf(-60, 109, 0, 0, 59, -110, -1, -1, -60, 109, 0, 0, 0, -1, 0, -1),
            byteArrayOf(-60, 109, 0, 0, 59, -110, -1, -1, -60, 109, 0, 0, 0, -1, 0, -1),
            byteArrayOf(-4, 0, -108, -19, 83, 1, 126, 8, 80, -61, 0, 1, -23, -8, -50, 19),
            byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        )
    },
    sector5 = Sector().apply {
        data = arrayOf(
            byteArrayOf(12, 49, 84, 1, 0, 0, -28, 12, 0, 0, 0, 0, 0, 0, -11, 110),
            byteArrayOf(2, 3, 12, 49, 84, 0, 109, 65, -44, 102, 0, 0, 0, 0, 0, 0),
            byteArrayOf(2, 3, 12, 49, 84, 0, 109, 65, -44, 102, 0, 0, 0, 0, 0, 0),
            byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        )
    }
)

fun getFakeLiveDataDump(): LiveData<Result<Dump, Exception>> = liveData {
    emit(Result.of(getFakeDump()))
}