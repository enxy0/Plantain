package kek.enxy.plantwriter.presentation.main.model

import kek.enxy.data.readwrite.model.Dump

sealed class DumpState {
    object Initial : DumpState()
    object Loading : DumpState()
    data class Content(val dump: Dump) : DumpState()
    data class Error(val exception: Throwable) : DumpState()
}
