package kek.plantain.utils.extensions

import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map

/**
 * Updates MutableLiveData value after applied changes in fn().
 * Useful when you need to update specific field of LiveData.value and to notify the observers.
 */
inline fun <V> MutableLiveData<Result<V, Exception>>.update(fn: V.() -> Unit) {
    postValue(value?.map { result -> result.apply(fn) })
}