package kek.plantain.ui.info

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.compose.Composable
import androidx.core.content.ContextCompat.getSystemService
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import kek.plantain.ui.theme.ThemedPreview
import kek.plantain.utils.WrongSectorKeyException
import java.io.IOException


@Composable fun FailureContent(exception: Exception) {
    val errorText = when (exception) {
        is IOException -> "Не удалось прочитать карту, возможно вы убрали ее слишком рано!"
        is WrongSectorKeyException -> "Не удалось прочитать карту, возможно это не Подорожник!"
        else -> "Не удалось прочитать карту, попробуйте еще раз!"
    }
    val context = ContextAmbient.current
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Spacer(modifier = Modifier.preferredHeight(20.dp))
        Text(
            text = "\uD83D\uDE14",
            style = MaterialTheme.typography.h6,
            fontSize = 80.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.gravity(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.preferredHeight(6.dp))
        Text(
            text = "Произошла ошибка",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.gravity(Alignment.CenterHorizontally)
        )
        Text(
            text = errorText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.gravity(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        Text(
            text = "Exception: $exception",
            style = MaterialTheme.typography.overline,
            modifier = Modifier.gravity(Alignment.CenterHorizontally).clickable(
                onClick = {
                    copyToClipBoard(context, "StackTrace", Log.getStackTraceString(exception))
                }
            )
        )
    }
}

@Preview(name = "Default Failure")
@Composable
fun DefaultFailurePreview() {
    ThemedPreview {
        FailureContent(WrongSectorKeyException())
    }
}

@Preview(name = "Dark Failure")
@Composable
fun DarkFailurePreview() {
    ThemedPreview(darkTheme = true) {
        FailureContent(WrongSectorKeyException())
    }
}

fun copyToClipBoard(context: Context, label: String, text: String) {
    val clipboard: ClipboardManager? = getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText(label, text)
    clipboard?.setPrimaryClip(clip)
}