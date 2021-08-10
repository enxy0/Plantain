package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface ScanContract {
    val resolveIntentFlow: Flow<Event<Intent>>
    fun openCurrentDumpDetails(dump: Dump)
    fun openDumps()
}
