package kek.enxy.plantwriter.presentation.main.scan

import android.app.Application
import android.content.Intent
import android.nfc.Tag
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.read.ReadTagParams
import kek.enxy.plantwriter.presentation.common.extensions.nfcTag
import kek.enxy.plantwriter.presentation.common.extensions.nfcTagId
import kek.enxy.plantwriter.presentation.main.model.DumpState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class ScanViewModel(
    application: Application,
    private val readDumpUseCase: ReadDumpUseCase
) : AndroidViewModel(application) {

    private val _plantainStateFlow = MutableStateFlow(listOf(Unit))
    val plantainStateFlow: StateFlow<List<Unit>> = _plantainStateFlow.asStateFlow()

    private val _dumpsStateFlow = MutableStateFlow(listOf(Unit))
    val dumpsStateFlow: StateFlow<List<Unit>> = _dumpsStateFlow.asStateFlow()

    private val _dumpStateFlow = MutableStateFlow<DumpState>(DumpState.Initial)
    val dumpStateFlow: StateFlow<DumpState> = _dumpStateFlow.asStateFlow()

    val dump: Dump? get() = (_dumpStateFlow.value as? DumpState.Content)?.dump

    private var job: Job? = null

    fun setNfcIntent(intent: Intent) {
        val tagId = intent.nfcTagId
        val tag = intent.nfcTag
        if (tagId != null && tag != null) {
            readDumpData(tagId, tag)
        }
    }

    private fun readDumpData(tagId: String, tag: Tag) {
        job?.cancel()
        job = readDumpUseCase(ReadTagParams(tagId, tag))
            .onStart {
                _dumpStateFlow.emit(DumpState.Loading)
            }
            .onEach { result ->
                result
                    .onSuccess { dump ->
                        _dumpStateFlow.emit(DumpState.Content(dump))
                    }
                    .onFailure { exception ->
                        _dumpStateFlow.emit(DumpState.Error(exception))
                        Logger.e(exception, exception.message.orEmpty())
                    }
            }
            .launchIn(viewModelScope)
    }
}
