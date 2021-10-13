package kek.enxy.plantwriter.presentation.main.dumps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.GetDumpsUseCase
import kek.enxy.domain.dumps.RemoveDumpUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class DumpsViewModel(
    private val getDumpsUseCase: GetDumpsUseCase,
    private val removeDumpUseCase: RemoveDumpUseCase
) : ViewModel() {

    private val _dumpsStateFlow = MutableStateFlow<List<Dump>>(emptyList())
    val dumpsStateFlow: StateFlow<List<Dump>> = _dumpsStateFlow.asStateFlow()

    private val _createDumpStateFlow = MutableStateFlow<List<Unit>>(emptyList())
    val createDumpStateFlow = _createDumpStateFlow.asStateFlow()

    private var removeJob: Job? = null

    init {
        collectDumps()
    }

    fun getEmptyDump(): Dump = Dump.empty()

    private fun collectDumps() {
        getDumpsUseCase(Unit)
            .onEach { result ->
                result
                    .onSuccess { dumps -> _dumpsStateFlow.value = dumps }
                    .onFailure { error -> Logger.e(error, error.message.orEmpty()) }
                sendCreateDumpData()
            }
            .launchIn(viewModelScope)
    }

    fun removeDump(dump: Dump) {
        removeJob?.cancel()
        removeJob = removeDumpUseCase(dump).launchIn(viewModelScope)
    }

    private fun sendCreateDumpData() {
        _createDumpStateFlow.value = listOf(Unit)
    }
}
