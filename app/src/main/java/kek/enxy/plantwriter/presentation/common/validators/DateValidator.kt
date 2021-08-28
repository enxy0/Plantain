package kek.enxy.plantwriter.presentation.common.validators

class DateValidator : Validator {
    private companion object {
        // Регулярка для валидации даты в формате dd.MM.yyyy HH:mm, где год может быть от 2010 до 2028
        // Т.к. это мин. и макс. значения, которые можно записать на подорожник
        private val dateTimeRegex = "(0[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.20(1[0-9]|2[0-8]) ([0-1]?[0-9]|2[0-3]):[0-5][0-9]".toRegex()
    }

    override fun validate(text: String) = text.matches(dateTimeRegex)
}
