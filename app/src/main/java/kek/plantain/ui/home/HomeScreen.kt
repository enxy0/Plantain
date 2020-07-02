package kek.plantain.ui.home

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.drawShadow
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kek.plantain.R
import kek.plantain.ui.theme.PlantainTheme

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantain") },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        bodyContent = {
            Body()
        }
    )
}


@Composable
fun Body() {
    Stack(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.preferredHeight(10.dp))
            Image(
                imageResource(id = R.drawable.plantain_card),
                modifier = Modifier
                    .drawShadow(4.dp, RoundedCornerShape(24.dp))
                    .gravity(Alignment.CenterHorizontally)
                    .preferredWidth(300.dp)
                    .preferredHeight(190.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
            )
            Spacer(modifier = Modifier.preferredHeight(10.dp))
            Text(
                "Приложите карту",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.gravity(Alignment.CenterHorizontally)
            )
            Text(
                "Пожалуйста, приложите электронную карту «Подорожник» с задней стороны телефона.",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.gravity(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlantainTheme {
        HomeScreen()
    }
}