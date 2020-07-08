package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.ui.foundation.Text
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.unit.dp
import com.github.kittinunf.result.Result
import kek.plantain.data.entity.Dump

@Composable
fun InfoScreen(dumpLiveData: LiveData<Result<Dump, Exception>>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantain") },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        bodyContent = {
            LiveDataComponent(dumpLiveData)
        }
    )
}

@Composable
fun LiveDataComponent(dumpLiveData: LiveData<Result<Dump, Exception>>) {
    val dump: Result<Dump, Exception>? by dumpLiveData.observeAsState()
    dump?.fold(
        success = { SuccessContent(dump = it) },
        failure = { FailureContent(exception = it) }
    )
}