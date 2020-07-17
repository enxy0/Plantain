package kek.plantain.ui.edit

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.material.FilledTextField
import androidx.ui.material.TextButton
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kek.plantain.ui.EditScreen
import kek.plantain.ui.EditScreen.*
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.NavigationViewModel
import kek.plantain.ui.theme.ThemedPreview
import kek.plantain.utils.getFakeNavigationViewModel

@Composable fun EditAlertDialog(
    onDumpChange: (EditScreenType, String) -> Unit,
    navigation: NavigationViewModel
) {
    var dialogIsShown by savedInstanceState { false }

    dialogIsShown = when (navigation.editScreen) {
        !is Closed ->
            true
        else ->
            false
    }

    if (dialogIsShown) {
        EditContent(
            onDumpChange = onDumpChange,
            navigation = navigation
        )
    }
}

@Composable fun EditContent(
    onDumpChange: (EditScreenType, String) -> Unit,
    navigation: NavigationViewModel
) {
    val (label, initialText) = getEditScreenValues(navigation.editScreen)
    var text by savedInstanceState { initialText }

    AlertDialog(
        title = { Text("Изменить") },
        onCloseRequest = { navigation.navigateTo(Closed) },
        shape = RoundedCornerShape(8.dp),
        confirmButton = {
            Button(
                onClick = {
                    onDumpChange(navigation.editScreen.type, text)
                    navigation.navigateTo(Closed)
                },
                elevation = 0.dp,
                text = { Text("Сохранить") },
                shape = RoundedCornerShape(8.dp)
            )
        },
        dismissButton = {
            TextButton(
                onClick = { navigation.navigateTo(Closed) },
                text = { Text("Отменить") },
                shape = RoundedCornerShape(8.dp)
            )
        },
        text = {
            FilledTextField(
                value = text,
                keyboardType = KeyboardType.Number,
                onValueChange = { text = it },
                label = { Text(label) },
                imeAction = ImeAction.Done,
                onImeActionPerformed = { action, softwareController ->
                    if (action == ImeAction.Done) {
                        softwareController?.hideSoftwareKeyboard()
                        navigation.navigateTo(Closed)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

fun getEditScreenValues(editScreen: EditScreen): Pair<String, String> = when (editScreen) {
    is CountScreen -> Pair("Количество", editScreen.count.toString())
    is MoneyScreen -> Pair("Сумма", editScreen.rubles.toString())
    is DateScreen -> Pair("Дата", editScreen.date.toString())
    Closed -> Pair("", "")
}

@Preview
@Composable
fun DefaultEditScreen() {
    ThemedPreview {
        EditAlertDialog(
            onDumpChange = { _, _ -> },
            navigation = getFakeNavigationViewModel()
        )
    }
}

@Preview
@Composable
fun DarkEditScreen() {
    ThemedPreview(darkTheme = true) {
        EditAlertDialog(
            onDumpChange = { _, _ -> },
            navigation = getFakeNavigationViewModel()
        )
    }
}
