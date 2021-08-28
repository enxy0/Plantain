package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Count(
    val raw: Int
) : Parcelable {

    companion object {
        fun parse(text: String) = Count(text.trim().toInt())
    }

    override fun toString(): String = raw.toString()
}
