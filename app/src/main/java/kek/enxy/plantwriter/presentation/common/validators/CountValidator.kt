package kek.enxy.plantwriter.presentation.common.validators

class CountValidator : Validator {
    override fun validate(text: String): Boolean {
        val count = text.toIntOrNull()
        return count != null && count in 0..255
    }
}
