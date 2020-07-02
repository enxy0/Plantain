package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.fillMaxSize
import androidx.ui.livedata.observeAsState
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.Build
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.Result.Failure
import com.github.kittinunf.result.Result.Success
import kek.plantain.data.entity.Dump
import kek.plantain.ui.theme.PlantainTheme
import kek.plantain.utils.getFakeLiveDataDump

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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                text = { Text("Hack it") },
                icon = { Icon(asset = Icons.Outlined.Build) },
                contentColor = Color.Black
            )
        },
        bodyContent = {
            VerticalScroller(modifier = Modifier.fillMaxSize()) {
                LiveDataComponent(dumpLiveData)
            }
        }
    )
}

@Composable
fun LiveDataComponent(dumpLiveData: LiveData<Result<Dump, Exception>>) {
    val dump: Result<Dump, Exception>? by dumpLiveData.observeAsState()
    if (dump != null) {
        if (dump is Success)
            SuccessContent(dump = dump!!.get())
        if (dump is Failure) {
            FailureContent()
        }
    } else {
//        LoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    PlantainTheme {
        InfoScreen(dumpLiveData = getFakeLiveDataDump())
    }
}