package kek.enxy.plantwriter.presentation.common.validators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ValidateObserver(
    scope: CoroutineScope,
    private val textFlow: StateFlow<String>,
    private val errorFlow: MutableStateFlow<String>,
    private val validators: List<ValidatorWrapper>,
    private val onChanged: (String) -> Unit = {}
) {
    constructor(
        scope: CoroutineScope,
        textFlow: StateFlow<String>,
        errorFlow: MutableStateFlow<String>,
        validator: ValidatorWrapper,
        onChanged: (String) -> Unit = {}
    ) : this(scope, textFlow, errorFlow, listOf(validator), onChanged)

    init {
        textFlow
            .onEach { text ->
                if (validateInternal(text)) {
                    onChanged(text)
                }
            }
            .launchIn(scope)
    }

    fun validate(): Boolean = validateInternal(textFlow.value)

    private fun validateInternal(text: String): Boolean {
        val error = validators
            .firstOrNull { wrapper -> !wrapper.validator.validate(text) }
            ?.takeIf { text.isNotBlank() }
        errorFlow.value = error?.message.orEmpty()
        return error == null
    }
}
