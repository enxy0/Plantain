package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Count(
    val raw: Int
) : Parcelable {
    override fun toString(): String = raw.toString()
}
