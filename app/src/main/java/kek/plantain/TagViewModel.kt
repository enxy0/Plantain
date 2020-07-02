package kek.plantain

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.Result
import kek.plantain.data.CardReader
import kek.plantain.data.entity.Dump
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagViewModel : ViewModel() {
    private val _dump: MutableLiveData<Result<Dump, Exception>> = MutableLiveData()
    val dump: LiveData<Result<Dump, Exception>> = _dump

    fun readNfcTag(intent: Intent) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = CardReader.readNfcTag(intent)
//            delay(3000)
            _dump.postValue(result)
        }
    }
}