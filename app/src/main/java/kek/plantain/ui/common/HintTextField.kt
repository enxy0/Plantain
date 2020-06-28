package kek.plantain.ui.common

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.foundation.drawBackground
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import kek.plantain.ui.theme.fira
import kek.plantain.ui.theme.textFieldBackground

@Composable
fun HintTextField(
    hint: String,
    modifier: Modifier = Modifier.fillMaxWidth().padding(12.dp),
    textStyle: TextStyle = TextStyle(fontSize = 40.sp, fontFamily = fira)
) {
    val state = savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }
    Stack(modifier = Modifier.drawBackground(MaterialTheme.colors.textFieldBackground)) {
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = textStyle,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.NoAction,
            modifier = modifier
        )
        if (state.value.text.isEmpty()) {
            Text(
                text = hint,
                fontSize = textStyle.fontSize,
                color = Color.Gray,
                modifier = modifier.matchParentSize()
            )
        }
    }
}

@Preview(name = "HintTextField", showBackground = true)
@Composable
fun HintTextFieldPreview() {
    HintTextField(hint = "Enter something")
}