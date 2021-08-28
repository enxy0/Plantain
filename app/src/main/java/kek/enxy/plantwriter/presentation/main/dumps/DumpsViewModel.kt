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

    private val _createDumpStateFlow = MutableStateFlow(emptyList<Unit>())
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
                    .onSuccess { dumps ->
                        _dumpsStateFlow.value = dumps
                        _createDumpStateFlow.value = listOf(Unit)
                    }
                    .onFailure {
                        Logger.e(it, it.message.orEmpty())
                    }
            }
            .launchIn(viewModelScope)
    }

    fun removeDump(dump: Dump) {
        removeJob?.cancel()
        removeJob = removeDumpUseCase(dump).launchIn(viewModelScope)
    }
}
