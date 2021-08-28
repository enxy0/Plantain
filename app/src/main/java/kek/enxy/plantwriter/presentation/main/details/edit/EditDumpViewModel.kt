package kek.enxy.plantwriter.presentation.main.details.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kek.enxy.data.readwrite.model.AppDate
import kek.enxy.data.readwrite.model.Count
import kek.enxy.data.readwrite.model.Rubles
import kek.enxy.plantwriter.presentation.common.validators.ValidateObserver
import kek.enxy.plantwriter.presentation.common.validators.ValidatorWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditDumpViewModel(
    validatorWrapper: ValidatorWrapper,
    private val initialType: EditDumpType
) : ViewModel() {
    var type: EditDumpType = initialType
        private set

    private val _textStateFlow = MutableStateFlow("")

    private val _errorStateFlow = MutableStateFlow("")
    val errorStateFlow: StateFlow<String> = _errorStateFlow.asStateFlow()

    private val validateObserver = ValidateObserver(
        scope = viewModelScope,
        textFlow = _textStateFlow,
        errorFlow = _errorStateFlow,
        validator = validatorWrapper
    ) { text ->
        type = if (text.isNotBlank()) {
            when (type) {
                is EditDumpType.Balance -> EditDumpType.Balance(Rubles.parse(text))
                is EditDumpType.GroundTravelTotal -> EditDumpType.GroundTravelTotal(Count.parse(text))
                is EditDumpType.LastPaymentAmount -> EditDumpType.LastPaymentAmount(Rubles.parse(text))
                is EditDumpType.LastPaymentDate -> EditDumpType.LastPaymentDate(AppDate.fromDate(text))
                is EditDumpType.LastUseAmount -> EditDumpType.LastUseAmount(Rubles.parse(text))
                is EditDumpType.LastUseDate -> EditDumpType.LastUseDate(AppDate.fromDate(text))
                is EditDumpType.UndergroundTravelTotal -> EditDumpType.UndergroundTravelTotal(Count.parse(text))
            }
        } else {
            initialType
        }
    }

    fun handleUserInput(text: String) {
        _textStateFlow.value = text
    }

    fun checkValueChange(): Boolean = initialType.value != type.value && validateObserver.validate()
}
