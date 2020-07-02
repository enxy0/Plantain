package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import kek.plantain.ui.theme.PlantainTheme

@Composable
fun FailureContent() {
    Stack(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Box(Modifier.gravity(Alignment.Center)) {
            Column {
                Text(
                    text = "\uD83D\uDE14",
                    style = MaterialTheme.typography.h6,
                    fontSize = 80.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.preferredHeight(6.dp))
                Text(
                    text = "Не удалось прочитать подорожник, попробуйте еще раз!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FailurePreview() {
    PlantainTheme {
        FailureContent()
    }
}
