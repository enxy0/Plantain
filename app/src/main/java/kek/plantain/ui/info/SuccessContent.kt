
package kek.plantain.ui.info

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kek.plantain.BuildConfig
import kek.plantain.R
import kek.plantain.data.entity.Count
import kek.plantain.data.entity.Date
import kek.plantain.data.entity.Dump
import kek.plantain.data.entity.Rubles
import kek.plantain.ui.EditScreen
import kek.plantain.ui.EditScreen.MoneyScreen
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.EditScreenType.BALANCE
import kek.plantain.ui.info.box.CountBox
import kek.plantain.ui.info.box.DateBox
import kek.plantain.ui.info.box.MoneyBox
import kek.plantain.ui.theme.ThemedPreview
import kek.plantain.ui.theme.onSurfaceTwice
import kek.plantain.utils.getFakeDump

@Composable fun SuccessContent(dump: Dump, navigateTo: (EditScreen) -> Unit) {
    VerticalScroller(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Balance(rubles = dump.balance, navigateTo = navigateTo)
        Spacer(modifier = Modifier.preferredHeight(20.dp))
        LastUsedSection(
            rubles = dump.lastPayedMoney,
            date = dump.lastUsedDate,
            navigateTo = navigateTo
        )
        TravelCountSection(
            subway = dump.subwayTravelCount,
            ground = dump.groundTravelCount,
            navigateTo = navigateTo
        )
        LastPaymentSection(
            rubles = dump.lastPaymentMoney,
            date = dump.lastPaymentDate,
            navigateTo = navigateTo
        )
        AboutSection()
    }
}

@Composable fun Balance(rubles: Rubles, navigateTo: (EditScreen) -> Unit) {
    Box(
        backgroundColor = MaterialTheme.colors.onSurfaceTwice,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigateTo(MoneyScreen(BALANCE, rubles)) })
    ) {
        Text(
            text = "$rubles₽",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(8.dp)
        )
    }
    Spacer(modifier = Modifier.preferredHeight(4.dp))
    Text("Баланс карты", style = MaterialTheme.typography.body2)
}

@Composable fun LastUsedSection(rubles: Rubles, date: Date, navigateTo: (EditScreen) -> Unit) {
    Text(text = "Последнее использование", style = MaterialTheme.typography.h6)
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    Row {
        MoneyBox(
            rubles = rubles,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            type = EditScreenType.LAST_PAYED_COST,
            navigate = navigateTo
        )
        Divider(
            modifier = Modifier
                .height(90.dp)
                .width(1.dp)
                .gravity(Alignment.CenterVertically)
        )
        DateBox(
            date = date,
            type = EditScreenType.LAST_USED_DATE,
            navigate = navigateTo,
            modifier = Modifier.gravity(Alignment.CenterVertically)
        )
    }
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}

@Composable fun TravelCountSection(subway: Count, ground: Count, navigateTo: (EditScreen) -> Unit) {
    Text(text = "Количество поездок", style = MaterialTheme.typography.h6)
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    Row {
        CountBox(
            asset = vectorResource(id = R.drawable.ic_metro),
            title = "Метро:",
            count = subway,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            type = EditScreenType.SUBWAY_TRAVEL_COUNT,
            navigate = navigateTo
        )
        Divider(
            modifier = Modifier
                .height(90.dp)
                .width(1.dp)
                .gravity(Alignment.CenterVertically)
        )
        CountBox(
            asset = vectorResource(id = R.drawable.ic_bus),
            title = "Наземка:",
            count = ground,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            type = EditScreenType.GROUND_TRAVEL_COUNT,
            navigate = navigateTo
        )
    }
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}

@Composable fun LastPaymentSection(rubles: Rubles, date: Date, navigateTo: (EditScreen) -> Unit) {
    Text(text = "Последнее пополнение", style = MaterialTheme.typography.h6)
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    Row {
        MoneyBox(
            rubles = rubles,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            type = EditScreenType.LAST_PAYMENT_AMOUNT,
            navigate = navigateTo
        )
        Divider(
            modifier = Modifier
                .height(90.dp)
                .width(1.dp)
                .gravity(Alignment.CenterVertically)
        )
        DateBox(
            date = date,
            modifier = Modifier.gravity(Alignment.CenterVertically),
            type = EditScreenType.LAST_PAYMENT_DATE,
            navigate = navigateTo
        )
    }
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}

@Composable fun AboutSection() {
    Text(
        text = "Версия приложения: ${BuildConfig.VERSION_NAME}",
        style = MaterialTheme.typography.body2
    )
    Text(
        text = "Автор: enxy0",
        style = MaterialTheme.typography.body2
    )
}

@Preview(name = "Default Success")
@Composable
fun DefaultSuccessPreview() {
    ThemedPreview {
        SuccessContent(getFakeDump()) {}
    }
}

@Preview(name = "Dark Success")
@Composable
fun DarkSuccessPreview() {
    ThemedPreview(darkTheme = true) {
        SuccessContent(getFakeDump()) {}
    }
}