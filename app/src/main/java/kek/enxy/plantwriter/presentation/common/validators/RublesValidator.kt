package kek.enxy.plantwriter.presentation.common.validators

class RublesValidator : Validator {
    override fun validate(text: String): Boolean {
        val rubles = text.toIntOrNull()
        return rubles != null && rubles in 0..167772
    }
}
