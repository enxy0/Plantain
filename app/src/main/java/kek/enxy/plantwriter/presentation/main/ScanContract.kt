package kek.enxy.plantwriter.presentation.main

import kotlinx.coroutines.flow.StateFlow

interface ScanContract {
    val resolveIntentFlow: StateFlow<CardState>
}
