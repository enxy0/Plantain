package kek.enxy.plantwriter.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kek.enxy.data.history.model.History
import kek.enxy.domain.history.GetFullHistoryUseCase
import kek.enxy.domain.model.Event
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HistoryViewModel(
    private val getFullHistoryUseCase: GetFullHistoryUseCase
) : ViewModel() {

    private val _historyLiveData = MutableLiveData<List<History>>()
    val historyLiveData: LiveData<List<History>> get() = _historyLiveData

    private val _errorLiveData = MutableLiveData<Event<String>>()
    val errorLiveData: LiveData<Event<String>> get() = _errorLiveData

    init {
        collectHistory()
    }

    private fun collectHistory() {
        getFullHistoryUseCase(Unit)
            .onEach { result ->
                result
                    .onSuccess {

                    }
                    .onFailure {

                    }

            }
            .launchIn(viewModelScope)
    }
}
