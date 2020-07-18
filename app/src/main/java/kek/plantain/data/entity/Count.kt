package kek.plantain.data.entity

data class Count (val raw: Int) {
    companion object {
        fun isValid(input: String): Boolean =
            input.matches("^\\d+$".toRegex())
    }

    override fun toString(): String = raw.toString()
}

fun Int.toCount() = Count(this)

fun String.toCount() = Count(this.toInt())

fun String.toCountOr(default: Count) = if (Count.isValid(this)) toCount() else default
