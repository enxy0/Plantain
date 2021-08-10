package kek.enxy.plantwriter.presentation.main.scan

import android.app.Application
import android.content.Intent
import android.nfc.Tag
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.read.ReadTagParams
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.presentation.common.extensions.getString
import kek.enxy.plantwriter.presentation.common.extensions.nfcTag
import kek.enxy.plantwriter.presentation.common.extensions.nfcTagId
import kek.enxy.plantwriter.presentation.main.model.DumpState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class ScanViewModel(
    application: Application,
    private val readDumpUseCase: ReadDumpUseCase
) : AndroidViewModel(application) {

    private var nfcIntent: Intent? = null

    private val _dumpStateFlow = MutableStateFlow<DumpState>(DumpState.Initial)
    val dumpStateFlow: StateFlow<DumpState> = _dumpStateFlow.asStateFlow()

    val dump: Dump? get() = (_dumpStateFlow.value as? DumpState.Content)?.dump

    private val _logStateFlow = MutableStateFlow(getString(R.string.main_log_hint))
    val logStateFlow: StateFlow<String> = _logStateFlow.asStateFlow()

    private var job: Job? = null

    fun setNfcIntent(intent: Intent) {
        nfcIntent = intent
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
                _logStateFlow.value += createLogMessage(R.string.main_tag_found)
                _dumpStateFlow.emit(DumpState.Loading)
            }
            .onEach { result ->
                result
                    .onSuccess { dump ->
                        _logStateFlow.value += createLogMessage(R.string.main_read_success)
                        _dumpStateFlow.emit(DumpState.Content(dump))
                    }
                    .onFailure { exception ->
                        _logStateFlow.value += createLogMessage(R.string.main_read_failure)
                        _dumpStateFlow.emit(DumpState.Error(exception))
                        Logger.e(exception, exception.message.orEmpty())
                    }
            }
            .launchIn(viewModelScope)
    }

    private fun createLogMessage(@StringRes resId: Int, newLine: Boolean = true): String {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(Calendar.getInstance().time)
        val newLineChar = if (newLine) "\n" else ""
        return "$newLineChar$time: ${getString(resId)}"
    }
}
