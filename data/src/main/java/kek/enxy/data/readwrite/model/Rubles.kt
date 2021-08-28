package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rubles(
    val raw: Int,
    val value: Int = raw / 100
) : Parcelable {

    companion object {
        fun parse(text: String) = Rubles(text.trim().toInt() * 100)
    }

    override fun toString() = value.toString()
}
