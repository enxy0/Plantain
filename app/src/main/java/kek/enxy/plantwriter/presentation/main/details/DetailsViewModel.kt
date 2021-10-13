package kek.enxy.plantwriter.presentation.main.details

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.GetLastDumpNumberUseCase
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.model.Event
import kek.enxy.domain.write.WriteDumpUseCase
import kek.enxy.domain.write.model.WriteDumpParams
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.presentation.common.extensions.context
import kek.enxy.plantwriter.presentation.common.extensions.getString
import kek.enxy.plantwriter.presentation.common.extensions.nfcTag
import kek.enxy.plantwriter.presentation.common.extensions.nfcTagId
import kek.enxy.plantwriter.presentation.common.getTextForUser
import kek.enxy.plantwriter.presentation.main.details.edit.EditDumpType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(
    application: Application,
    private val initialDump: Dump,
    private val saveDumpUseCase: SaveDumpUseCase,
    private val writeDumpUseCase: WriteDumpUseCase,
    private val getLastDumpNumberUseCase: GetLastDumpNumberUseCase
) : AndroidViewModel(application) {

    private val _dumpStateFlow = MutableStateFlow(initialDump)
    val dumpStateFlow get() = _dumpStateFlow.asStateFlow()

    private val _saveResultLiveData = MutableLiveData<String>()
    val saveResultLiveData: LiveData<String> get() = _saveResultLiveData

    private val _writeResultLiveData = MutableLiveData<String>()
    val writeResultLiveData: LiveData<String> get() = _writeResultLiveData

    val dump: Dump
        get() = _dumpStateFlow.value

    var generatedDumpName: String = ""
        private set

    init {
        collectLastDumpNumber()
    }

    fun write(resolveIntentFlow: Flow<Event<Intent>>) {
        viewModelScope.launch {
            resolveIntentFlow.firstOrNull()?.peekContent()?.let { intent ->
                val tagId = intent.nfcTagId
                val tag = intent.nfcTag
                if (tagId != null && tag != null) {
                    writeDumpUseCase(WriteDumpParams(tagId, tag, dump)).collect { result ->
                        result
                            .onSuccess {
                                _writeResultLiveData.value = getString(R.string.details_write_dump_ok)
                            }
                            .onFailure { error ->
                                _writeResultLiveData.value = error.getTextForUser(context)
                            }
                    }
                }
            }
        }
    }

    fun save(name: String = dump.name) {
        val dump = _dumpStateFlow.value.copy(name = name)
        Logger.d("name = $name, dump = $dump")
        saveDumpUseCase(dump)
            .onEach { result ->
                _saveResultLiveData.value = if (result.isSuccess) {
                    getString(R.string.details_save_dump_ok)
                } else {
                    getString(R.string.details_save_dump_error)
                }
            }
            .onCompletion { _dumpStateFlow.value = dump }
            .launchIn(viewModelScope)
    }

    fun handleDumpUpdate(type: EditDumpType) {
        viewModelScope.launch(Dispatchers.Default) {
            val dump = _dumpStateFlow.value.copy()
            when (type) {
                is EditDumpType.Balance -> dump.balance = type.rubles
                is EditDumpType.GroundTravelTotal -> dump.groundTravelTotal = type.count
                is EditDumpType.LastPaymentAmount -> dump.lastPaymentAmount = type.rubles
                is EditDumpType.LastPaymentDate -> dump.lastPaymentDate = type.date
                is EditDumpType.LastUseAmount -> dump.lastUseAmount = type.rubles
                is EditDumpType.LastUseDate -> dump.lastUseDate = type.date
                is EditDumpType.UndergroundTravelTotal -> dump.undergroundTravelTotal = type.count
            }
            _dumpStateFlow.emit(dump)
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
