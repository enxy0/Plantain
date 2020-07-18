package kek.plantain.ui.info.box

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.DateRange
import androidx.ui.tooling.preview.Preview
import kek.plantain.data.entity.Date
import kek.plantain.data.entity.toDate
import kek.plantain.ui.EditScreen
import kek.plantain.ui.EditScreen.DateScreen
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.EditScreenType.LAST_USED_DATE
import kek.plantain.ui.common.DetailsBox
import kek.plantain.ui.theme.ThemedPreview

@Composable fun DateBox(
    asset: VectorAsset = Icons.Outlined.DateRange,
    title: String = "Дата:",
    date: Date,
    type: EditScreenType,
    navigate: (EditScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    DetailsBox(
        asset = asset,
        title = title,
        summary = date.toString(),
        onClick = { navigate(DateScreen(type, date)) },
        modifier = modifier
    )
}


@Preview("Default Preview", showBackground = true)
@Composable
fun DefaultDateBoxPreview() {
    ThemedPreview(darkTheme = false) {
        DateBox(
            date = "26.06.2020 15:48".toDate(),
            type = LAST_USED_DATE,
            navigate = {}
        )
    }
}

@Preview("Dark Preview", showBackground = true)
@Composable
fun DarkDateBoxPreview() {
    ThemedPreview(darkTheme = true) {
        DateBox(
            date = "26.06.2020 15:48".toDate(),
            type = LAST_USED_DATE,
            navigate = {}
        )
    }
}