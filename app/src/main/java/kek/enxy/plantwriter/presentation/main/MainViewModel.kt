package kek.enxy.plantwriter.presentation.main

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kek.enxy.domain.model.Event
import kek.enxy.domain.read.ReadDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class MainViewModel(
    private val writeDumpUseCase: WriteDumpUseCase,
    private val readDumpUseCase: ReadDumpUseCase
) : ViewModel() {

    private val _writeResultFlow =
        MutableSharedFlow<Event<Boolean>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val writeResultFlow: SharedFlow<Event<Boolean>> = _writeResultFlow.asSharedFlow()

    private val _dumpStateFlow = MutableStateFlow<DumpState>(DumpState.Initial)
    val dumpStateFlow: StateFlow<DumpState> = _dumpStateFlow.asStateFlow()

    fun writeDumpData(tag: Tag) {
        writeDumpUseCase(tag)
            .onEach { result ->
                _writeResultFlow.emit(Event(result.isSuccess))
            }
            .launchIn(viewModelScope)
    }

    fun readDumpData(tag: Tag) {
        _dumpStateFlow.tryEmit(DumpState.Loading)
        readDumpUseCase(tag)
            .onEach { result ->
                result
                    .onSuccess { dump ->
                        _dumpStateFlow.emit(DumpState.Content(dump))
                    }
                    .onFailure { exception ->
                        _dumpStateFlow.emit(DumpState.Error(exception))
                        Logger.e(exception, exception.message.orEmpty())
                    }
            }.launchIn(viewModelScope)
    }
}
