package kek.enxy.plantwriter.presentation.main.details

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.GetLastDumpNumberUseCase
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.presentation.common.extensions.context
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    application: Application,
    private val initialDump: Dump,
    private val saveDumpUseCase: SaveDumpUseCase,
    private val writeDumpUseCase: WriteDumpUseCase,
    private val getLastDumpNumberUseCase: GetLastDumpNumberUseCase
) : AndroidViewModel(application) {

    private val _dumpStateFlow = MutableStateFlow(initialDump)
    val dumpStateFlow = _dumpStateFlow.asStateFlow()

    private val _saveResultLiveData = MutableLiveData<Boolean>()
    val saveResultLiveData: LiveData<Boolean> get() = _saveResultLiveData

    private val _writeResultLiveData = MutableLiveData<Boolean>()
    val writeResultLiveData: LiveData<Boolean> get() = _writeResultLiveData

    val dump: Dump
        get() = _dumpStateFlow.value

    var generatedDumpName: String = ""
        private set

    init {
        collectLastDumpNumber()
    }

    fun saveDump(name: String = dump.name) {
        val dump = _dumpStateFlow.value.copy(name = name)
        Logger.d("name = $name, dump = $dump")
        saveDumpUseCase(dump)
            .onEach { result -> _saveResultLiveData.value = result.isSuccess }
            .onCompletion { _dumpStateFlow.value = dump }
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

    private fun collectLastDumpNumber() {
        getLastDumpNumberUseCase(Unit)
            .onEach { result ->
                result
                    .onSuccess { number ->
                        generatedDumpName = context.getString(R.string.name_dump_placeholder, number + 1)
                    }
                    .onFailure {
                        Logger.e(it, it.message.orEmpty())
                    }
            }
            .launchIn(viewModelScope)
    }
}
