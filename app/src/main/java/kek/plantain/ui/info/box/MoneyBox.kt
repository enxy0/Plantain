package kek.plantain.ui.info.box

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.Info
import androidx.ui.tooling.preview.Preview
import kek.plantain.data.entity.Rubles
import kek.plantain.ui.EditScreen
import kek.plantain.ui.EditScreen.MoneyScreen
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.EditScreenType.BALANCE
import kek.plantain.ui.common.DetailsBox
import kek.plantain.ui.theme.ThemedPreview

@Composable fun MoneyBox(
    asset: VectorAsset = Icons.Outlined.Info,
    title: String = "Сумма:",
    rubles: Rubles,
    type: EditScreenType,
    navigate: (EditScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    DetailsBox(
        asset = asset,
        title = title,
        summary = "$rubles₽",
        onClick = { navigate(MoneyScreen(type, rubles)) },
        modifier = modifier
    )
}


@Preview("Default Preview", showBackground = true)
@Composable
fun DefaultMoneyBoxPreview() {
    ThemedPreview(darkTheme = false) {
        MoneyBox(
            rubles = Rubles(28100),
            type = BALANCE,
            navigate = {}
        )
    }
}

@Preview("Dark Preview", showBackground = true)
@Composable
fun DarkMoneyBoxPreview() {
    ThemedPreview(darkTheme = true) {
        MoneyBox(
            rubles = Rubles(28100),
            type = BALANCE,
            navigate = {}
        )
    }
}