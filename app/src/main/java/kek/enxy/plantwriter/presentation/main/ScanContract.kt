package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import kek.enxy.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface ScanContract {
    val resolveIntentFlow: Flow<Event<Intent>>
}
