package kek.enxy.plantwriter.presentation.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.domain.dumps.SaveDumpUseCase
import kek.enxy.domain.write.WriteDumpUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsViewModel(
    val dump: Dump,
    private val saveDumpUseCase: SaveDumpUseCase,
    private val writeDumpUseCase: WriteDumpUseCase
) : ViewModel() {

    private val _saveResultLiveData = MutableLiveData<Boolean>()
    val saveResultLiveData: LiveData<Boolean> get() = _saveResultLiveData

    private val _writeResultLiveData = MutableLiveData<Boolean>()
    val writeResultLiveData: LiveData<Boolean> get() = _writeResultLiveData

    fun save() {
        saveDumpUseCase(dump)
            .onEach { result -> _saveResultLiveData.value = result.isSuccess }
            .launchIn(viewModelScope)
    }

    fun write() {
//        writeDumpUseCase(tag, dump)
//            .onEach { result -> _writeResultLiveData.value = result.isSuccess }
//            .launchIn(viewModelScope)
    }
}
