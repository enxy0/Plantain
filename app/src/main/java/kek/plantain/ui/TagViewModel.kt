package kek.plantain.ui

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.success
import kek.plantain.data.CardReader
import kek.plantain.data.entity.Dump
import kek.plantain.utils.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagViewModel : ViewModel() {
    private val _dump: MutableLiveData<Result<Dump, Exception>> = MutableLiveData()
    val dump: LiveData<Result<Dump, Exception>> = _dump
    private val _isWriteSuccessful: MutableLiveData<Result<Boolean, Exception>> = MutableLiveData()
    val isWriteSuccessful: LiveData<Result<Boolean, Exception>> = _isWriteSuccessful

    fun readNfcTag(intent: Intent) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                CardReader.readNfcTag(intent)
            }
            _dump.value = result
        }
    }

    fun writeNfcTag(intent: Intent) {
        dump.value?.success {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) { CardReader.writeNfcTag(intent, it) }
                _isWriteSuccessful.value = result
            }
        }
    }

    fun overwriteDumpValue(type: EditScreenType, input: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _dump.update {
                overwrite(type, input)
            }
        }
    }
}