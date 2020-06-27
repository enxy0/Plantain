package kek.plantain.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.ExtendedFloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.Build
import androidx.ui.material.icons.outlined.DateRange
import androidx.ui.material.icons.outlined.Info
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kek.plantain.R
import kek.plantain.home.DetailsBox
import kek.plantain.home.HintTextField

@Composable
fun PlantainApp() {
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
            VerticalScroller {
                Body()
            }
        }
    )
}

@Composable
fun Body() {
    Column(modifier = Modifier.padding(16.dp)) {
        Box(children = { HintTextField(hint = "1337") })
        Spacer(modifier = Modifier.preferredHeight(4.dp))
        Text("Enter amount in Rubles (₽)", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.preferredHeight(20.dp))
        Text(text = "Card information", style = MaterialTheme.typography.h6)
        Row(modifier = Modifier.padding(top = 8.dp)) {
            DetailsBox(asset = Icons.Outlined.DateRange, title = "Last used:", summary = "27.06.20")
            DetailsBox(asset = Icons.Outlined.Info, title = "Last payed:", summary = "31₽")
        }
        Spacer(modifier = Modifier.preferredHeight(20.dp))
        Text(text = "Trips in June", style = MaterialTheme.typography.h6)
        Row(modifier = Modifier.padding(top = 8.dp)) {
            DetailsBox(
                asset = vectorResource(id = R.drawable.ic_metro),
                title = "Metro:",
                summary = "0"
            )
            DetailsBox(
                asset = vectorResource(id = R.drawable.ic_bus),
                title = "Land transport:",
                summary = "6"
            )
        }
        Spacer(modifier = Modifier.preferredHeight(20.dp))
        Text(text = "Money recharge", style = MaterialTheme.typography.h6)
        Row(modifier = Modifier.padding(top = 8.dp)) {
            DetailsBox(
                asset = Icons.Outlined.Info,
                title = "Money recharge date:",
                summary = "01.01.2010 00:00"
            )
            DetailsBox(
                asset = Icons.Outlined.Info,
                title = "Money recharge amount:",
                summary = "1337₽"
            )
        }
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Composable
fun DefaultPreview() {
    PlantainTheme {
        PlantainApp()
    }
}

@Preview(name = "Dark Theme", showBackground = true)
@Composable
fun Preview() {
    PlantainTheme(darkTheme = true) {
        PlantainApp()
    }
}