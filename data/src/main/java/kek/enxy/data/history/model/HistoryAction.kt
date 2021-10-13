package kek.enxy.data.history.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class HistoryAction {
    READ, WRITE
}
