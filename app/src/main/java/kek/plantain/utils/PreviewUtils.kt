package kek.plantain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump
import kek.plantain.data.entity.Sector
import kek.plantain.ui.EditScreen.MoneyScreen
import kek.plantain.ui.EditScreenType.BALANCE
import kek.plantain.ui.NavigationViewModel

fun getFakeDump(): Dump = Dump(
    tagId = "4445772F66780",
    sector4 = Sector().apply {
        data = arrayOf(
            byteArrayOf(0, -62, 1, 0, -1, 61, -2, -1, 0, -62, 1, 0, 0, -1, 0, -1),
            byteArrayOf(0, -62, 1, 0, -1, 61, -2, -1, 0, -62, 1, 0, 0, -1, 0, -1),
            byteArrayOf(-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 68, -74, 56),
            byteArrayOf(0, 0, 0, 0, 0, 0, 72, 119, -117, 0, 0, 0, 0, 0, 0, 0)
        )
    },
    sector5 = Sector().apply {
        data = arrayOf(
            byteArrayOf(85, 16, 84, 3, 32, 5, 48, 17, 0, 0, 0, 0, 0, 0, 12, 40),
            byteArrayOf(0, 2, 85, 16, 84, 0, -68, 11, -26, -123, 0, 0, 0, 0, 0, 0),
            byteArrayOf(0, 2, 85, 16, 84, 0, -68, 11, -26, -123, 0, 0, 0, 0, 0, 0),
            byteArrayOf(0, 0, 0, 0, 0, 0, 127, 7, -120, 0, 0, 0, 0, 0, 0, 0)
        )
    }
)

fun getFakeLiveDataDump(): LiveData<Result<Dump, Exception>> = liveData {
    emit(Result.of(getFakeDump()))
}

fun getFakeEditBalance(): MoneyScreen = MoneyScreen(BALANCE, 3100.toRubles())

fun getFakeNavigationViewModel(): NavigationViewModel = NavigationViewModel(SavedStateHandle()).apply {
    navigateTo(MoneyScreen(BALANCE, 135000.toRubles()))
}