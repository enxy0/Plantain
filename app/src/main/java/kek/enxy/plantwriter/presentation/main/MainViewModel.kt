package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.lifecycle.ViewModel
import kek.enxy.domain.model.Event
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : ViewModel() {

    private val _resolveIntentFlow = MutableSharedFlow<Event<Intent>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val resolveIntentFlow: SharedFlow<Event<Intent>> = _resolveIntentFlow.asSharedFlow()

    fun createIntentEvent(intent: Intent?) {
        if (intent == null || intent.action != NfcAdapter.ACTION_TECH_DISCOVERED) return
        _resolveIntentFlow.tryEmit(Event(intent))
    }
}
