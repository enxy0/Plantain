package kek.enxy.plantwriter.presentation.main.dumps

import androidx.lifecycle.ViewModel
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.data.readwrite.model.Rubles
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DumpsViewModel : ViewModel() {

    private val _dumpsSharedFlow = MutableSharedFlow<List<Dump>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val dumpSharedFlow: SharedFlow<List<Dump>> = _dumpsSharedFlow.asSharedFlow()

    init {
        _dumpsSharedFlow.tryEmit(
            listOf(
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                ),
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                ),
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                ),
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                ),
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                ),
                Dump(
                    id = 0,
                    "041E62DAE36480",
                    "Подорожник с 500 руб.",
                    Rubles(50000),
                    Rubles(0),
                    AppDate(237894238),
                    Rubles(0),
                    AppDate(237894238),
                    Count(0),
                    Count(0)
                )
            )
        )
    }
}
