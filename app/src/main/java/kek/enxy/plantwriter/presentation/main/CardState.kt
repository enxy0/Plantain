package kek.enxy.plantwriter.presentation.main

import android.content.Intent
import kek.enxy.domain.model.Event

sealed class CardState {
    object Empty : CardState()
    data class Connected(val event: Event<Intent>) : CardState()
}
