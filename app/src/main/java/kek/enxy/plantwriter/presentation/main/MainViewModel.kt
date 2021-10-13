package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.lifecycle.ViewModel
import kek.enxy.domain.model.Event
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val _resolveIntentFlow = MutableStateFlow<CardState>(CardState.Empty)
    val resolveIntentFlow: StateFlow<CardState> = _resolveIntentFlow.asStateFlow()

    fun createIntentEvent(intent: Intent?) {
        if (intent == null || intent.action != NfcAdapter.ACTION_TECH_DISCOVERED) return
        _resolveIntentFlow.tryEmit(CardState.Connected(Event(intent)))
    }
}
