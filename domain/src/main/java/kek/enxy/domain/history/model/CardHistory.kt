package kek.enxy.domain.history.model

import kek.enxy.data.history.model.History

data class CardHistory(
    val uid: String,
    val histories: List<History>
)
