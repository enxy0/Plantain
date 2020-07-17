package kek.plantain.ui

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kek.plantain.data.entity.Count
import kek.plantain.data.entity.Date
import kek.plantain.data.entity.Rubles
import kek.plantain.ui.EditScreen.*
import kek.plantain.ui.EditScreenType.*
import kek.plantain.utils.getMutableStateOf
import kek.plantain.utils.toCount
import kek.plantain.utils.toDate
import kek.plantain.utils.toRubles

enum class EditScreenType {
    BALANCE,
    LAST_PAYED_COST,
    LAST_PAYMENT_AMOUNT,
    LAST_PAYMENT_DATE,
    LAST_USED_DATE,
    GROUND_TRAVEL_COUNT,
    SUBWAY_TRAVEL_COUNT,
    NONE
}

sealed class EditScreen(val type: EditScreenType) {
    class CountScreen(type: EditScreenType, val count: Count) : EditScreen(type)
    class MoneyScreen(type: EditScreenType, val rubles: Rubles) : EditScreen(type)
    class DateScreen(type: EditScreenType, val date: Date) : EditScreen(type)
    object Closed : EditScreen(NONE)
}

private const val SCREEN_KEY = "edit_screen"
private const val SCREEN_NAME = "edit_screen_name"
private const val EXTRA_MONEY = "money"
private const val EXTRA_DATE = "date"
private const val EXTRA_COUNT = "count"

/**
 * Convert a screen to a bundle that can be stored in [SavedStateHandle]
 */
private fun EditScreen.toBundle(): Bundle {
    return bundleOf(SCREEN_NAME to type.name).also {
        when (this) {
            is CountScreen -> it.putInt(EXTRA_COUNT, count.raw)
            is MoneyScreen -> it.putInt(EXTRA_MONEY, rubles.raw)
            is DateScreen -> it.putInt(EXTRA_DATE, date.raw)
        }
    }
}

private fun Bundle.toScreen(): EditScreen {
    return when (valueOf(getStringOrThrow(SCREEN_NAME))) {
        BALANCE -> MoneyScreen(BALANCE, getRubles(EXTRA_MONEY))
        LAST_PAYED_COST -> MoneyScreen(LAST_PAYED_COST, getRubles(EXTRA_MONEY))
        LAST_PAYMENT_AMOUNT -> MoneyScreen(LAST_PAYMENT_AMOUNT, getRubles(EXTRA_MONEY))
        LAST_PAYMENT_DATE -> DateScreen(LAST_PAYMENT_DATE, getDate(EXTRA_DATE))
        LAST_USED_DATE -> DateScreen(LAST_USED_DATE, getDate(EXTRA_DATE))
        GROUND_TRAVEL_COUNT -> CountScreen(GROUND_TRAVEL_COUNT, getCount(EXTRA_COUNT))
        SUBWAY_TRAVEL_COUNT -> CountScreen(SUBWAY_TRAVEL_COUNT, getCount(EXTRA_COUNT))
        NONE -> Closed
    }
}

private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }

private fun Bundle.getRubles(key: String) = getInt(key).toRubles()

private fun Bundle.getCount(key: String) = getInt(key).toCount()

private fun Bundle.getDate(key: String) = getInt(key).toDate()

class NavigationViewModel(private val savedStateHandler: SavedStateHandle) : ViewModel() {
    var editScreen: EditScreen by savedStateHandler.getMutableStateOf<EditScreen>(
        key = SCREEN_KEY,
        default = Closed,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set

    @MainThread
    fun navigateTo(screen: EditScreen) {
        editScreen = screen
    }
}