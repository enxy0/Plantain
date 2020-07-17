package kek.plantain.data.entity

data class Count (val raw: Int) {
    companion object {
        fun isValid(input: String): Boolean =
            input.matches("^\\d+$".toRegex())
    }

    override fun toString(): String = raw.toString()
}