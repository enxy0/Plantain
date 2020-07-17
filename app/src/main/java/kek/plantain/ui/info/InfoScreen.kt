package kek.plantain.ui.info

import android.content.Intent
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.RowScope.gravity
import androidx.ui.layout.Stack
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import kek.plantain.R
import kek.plantain.ui.NavigationViewModel
import kek.plantain.ui.TagViewModel
import kek.plantain.ui.edit.EditAlertDialog
import kek.plantain.utils.extensions.toast

@Composable fun InfoScreen(
    navigation: NavigationViewModel,
    tagViewModel: TagViewModel,
    intent: Intent
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantain") },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        bodyContent = {
            LiveDataComponent(
                navigation = navigation,
                tagViewModel = tagViewModel
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Записать") },
                onClick = { tagViewModel.writeNfcTag(intent) },
                icon = { Icon(asset = vectorResource(id = R.drawable.ic_write)) })
        }
    )
}

@Composable fun LiveDataComponent(
    navigation: NavigationViewModel,
    tagViewModel: TagViewModel
) {
    Stack {
        val dump by tagViewModel.dump.observeAsState()
        val isWriteSuccessful by tagViewModel.isWriteSuccessful.observeAsState()
        val context = ContextAmbient.current
        dump?.fold(
            success = {
                SuccessContent(dump = it, navigateTo = navigation::navigateTo)
                EditAlertDialog(
                    onDumpChange = tagViewModel::overwriteDumpValue,
                    navigation = navigation
                )
            },
            failure = {
                FailureContent(exception = it)
            }
        )
        isWriteSuccessful?.fold(
            success = { toast(context = context, text = "Запись прошла успешно!") },
            failure = { toast(context = context, text = "При записи произошла ошибка. Попробуйте еще раз!") }
        )
    }
}