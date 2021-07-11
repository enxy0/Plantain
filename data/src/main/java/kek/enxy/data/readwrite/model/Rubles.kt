package kek.enxy.data.readwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rubles(
    private val raw: Int,
    val value: Int = raw / 100
) : Parcelable {

    override fun toString() = value.toString()
}
