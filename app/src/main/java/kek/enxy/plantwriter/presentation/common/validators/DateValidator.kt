package kek.enxy.plantwriter.presentation.common.validators

import kek.enxy.data.readwrite.model.AppDate

class DateValidator : Validator {
    private companion object {
        private const val DATE_MAX = 16581375 // 255 * 255 * 255
    }

    override fun validate(text: String) = try {
        AppDate.fromDate(text).raw in 0..DATE_MAX
    } catch (t: Throwable) {
        false
    }
}
