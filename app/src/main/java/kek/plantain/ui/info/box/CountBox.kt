package kek.plantain.ui.info.box

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import kek.plantain.R
import kek.plantain.data.entity.Count
import kek.plantain.ui.EditScreen
import kek.plantain.ui.EditScreen.CountScreen
import kek.plantain.ui.EditScreenType
import kek.plantain.ui.EditScreenType.GROUND_TRAVEL_COUNT
import kek.plantain.ui.common.DetailsBox
import kek.plantain.ui.theme.ThemedPreview
import kek.plantain.utils.toCount

@Composable fun CountBox(
    asset: VectorAsset,
    title: String,
    count: Count,
    type: EditScreenType,
    navigate: (EditScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    DetailsBox(
        asset = asset,
        title = title,
        summary = count.toString(),
        onClick = { navigate(CountScreen(type, count)) },
        modifier = modifier
    )
}


@Preview("Default Preview", showBackground = true)
@Composable
fun DefaultCountBoxPreview() {
    ThemedPreview(darkTheme = false) {
        CountBox(
            asset = vectorResource(id = R.drawable.ic_bus),
            title = "Наземка:",
            count = 3.toCount(),
            type = GROUND_TRAVEL_COUNT,
            navigate = { }
        )
    }
}

@Preview("Dark Preview", showBackground = true)
@Composable
fun DarkCountBoxPreview() {
    ThemedPreview(darkTheme = true) {
        CountBox(
            asset = vectorResource(id = R.drawable.ic_bus),
            title = "Наземка:",
            count = 3.toCount(),
            type = GROUND_TRAVEL_COUNT,
            navigate = { }
        )
    }
}