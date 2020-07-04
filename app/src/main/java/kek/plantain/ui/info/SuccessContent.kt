package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.DateRange
import androidx.ui.material.icons.outlined.Info
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kek.plantain.R
import kek.plantain.data.entity.Dump
import kek.plantain.ui.common.DetailsBox
import kek.plantain.ui.common.HintTextField
import kek.plantain.ui.theme.PlantainTheme
import kek.plantain.utils.getFakeDump

@Composable
fun SuccessContent(dump: Dump) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(children = { HintTextField(hint = "${dump.balance() / 100},${dump.balance() % 100}₽") })
            Spacer(modifier = Modifier.preferredHeight(4.dp))
            Text("Баланс карты", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.preferredHeight(20.dp))
            Text(text = "Последнее использование", style = MaterialTheme.typography.h6)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                DetailsBox(
                        asset = Icons.Outlined.Info,
                        title = "Сумма:",
                        summary = "${dump.lastPayedCost() / 100},${dump.lastPayedCost() % 100}₽"
                )
                Divider(modifier = Modifier.height(90.dp).width(1.dp))
                DetailsBox(
                        asset = Icons.Outlined.DateRange,
                        title = "Дата:",
                        summary = dump.lastUsedDate()
                )
            }
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            Text(text = "Количество поездок", style = MaterialTheme.typography.h6)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                DetailsBox(
                    asset = vectorResource(id = R.drawable.ic_metro),
                    title = "Метро:",
                    summary = dump.subwayTravelCount().toString()
                )
                Divider(modifier = Modifier.height(90.dp).width(1.dp))
                DetailsBox(
                    asset = vectorResource(id = R.drawable.ic_bus),
                    title = "Наземка:",
                    summary = dump.groundTravelCount().toString()
                )
            }
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            Text(text = "Последнее пополнение", style = MaterialTheme.typography.h6)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                DetailsBox(
                        asset = Icons.Outlined.Info,
                        title = "Сумма:",
                        summary = "${dump.lastPaymentAmount() / 100},${dump.lastPaymentAmount() % 100}₽"
                )
                Divider(modifier = Modifier.height(90.dp).width(1.dp))
                DetailsBox(
                        asset = Icons.Outlined.DateRange,
                        title = "Дата:",
                        summary = dump.lastPaymentDate()
                )
            }
        }
    }
}

@Preview(name = "Success", showBackground = true)
@Composable
fun DefaultPreview() {
    PlantainTheme {
        SuccessContent(getFakeDump())
    }
}