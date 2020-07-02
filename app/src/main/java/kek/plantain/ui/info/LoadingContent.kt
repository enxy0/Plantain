package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.LinearProgressIndicator
import androidx.ui.tooling.preview.Preview
import kek.plantain.ui.theme.PlantainTheme

@Composable
fun LoadingContent() {
    Stack(modifier = Modifier.fillMaxSize()) {
        LinearProgressIndicator(modifier = Modifier.gravity(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    PlantainTheme {
        LoadingContent()
    }
}