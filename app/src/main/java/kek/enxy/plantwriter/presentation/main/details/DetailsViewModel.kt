package kek.enxy.plantwriter.presentation.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsViewModel(
    private val initialDump: Dump,
    private val saveDumpUseCase: SaveDumpUseCase,
    private val writeDumpUseCase: WriteDumpUseCase
) : ViewModel() {

    private val _dumpStateFlow = MutableStateFlow(initialDump)
    val dumpStateFlow = _dumpStateFlow.asStateFlow()

    private val _saveResultLiveData = MutableLiveData<Boolean>()
    val saveResultLiveData: LiveData<Boolean> get() = _saveResultLiveData

    private val _writeResultLiveData = MutableLiveData<Boolean>()
    val writeResultLiveData: LiveData<Boolean> get() = _writeResultLiveData

    val dump: Dump
        get() = _dumpStateFlow.value

    fun save() {
        saveDumpUseCase(_dumpStateFlow.value)
            .onEach { result -> _saveResultLiveData.value = result.isSuccess }
            .launchIn(viewModelScope)
    }

    fun write() {
//        writeDumpUseCase(tag, dump)
//            .onEach { result -> _writeResultLiveData.value = result.isSuccess }
//            .launchIn(viewModelScope)
    }

    fun handleDumpUpdate(type: EditDumpType) {
        val dump = _dumpStateFlow.value
        _dumpStateFlow.value = when (type) {
            is EditDumpType.Balance -> dump.copy(balance = type.rubles)
            is EditDumpType.GroundTravelTotal -> dump.copy(groundTravelTotal = type.count)
            is EditDumpType.LastPaymentAmount -> dump.copy(lastPaymentAmount = type.rubles)
            is EditDumpType.LastPaymentDate -> dump.copy(lastPaymentDate = type.date)
            is EditDumpType.LastUseAmount -> dump.copy(lastUseAmount = type.rubles)
            is EditDumpType.LastUseDate -> dump.copy(lastUseDate = type.date)
            is EditDumpType.UndergroundTravelTotal -> dump.copy(undergroundTravelTotal = type.count)
        }
    }
}
