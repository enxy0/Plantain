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

    val isEmpty get() = raw == -1
    val isNotEmpty get() = !isEmpty

    override fun toString() = value.toString()
    operator fun plus(other: Rubles) = Rubles(raw + other.raw)
    operator fun minus(other: Rubles) = Rubles(raw - other.raw)
    operator fun compareTo(other: Rubles) = raw.compareTo(other.raw)
}
