package kek.plantain.ui.common

import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.ColorFilter
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.RowScope.weight
import androidx.ui.layout.Spacer
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.size
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.outlined.Info
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun DetailsBox(asset: VectorAsset, title: String, summary: String) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            .weight(1f)
    ) {
        Image(
            asset = asset,
            modifier = Modifier.size(26.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
        Text(title, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 5.dp))
        Spacer(modifier = Modifier.preferredHeight(1.dp))
        Text(summary, style = MaterialTheme.typography.body2)
    }
}

@Preview("Tile", showBackground = true)
@Composable
fun TilePreview() {
    DetailsBox(Icons.Outlined.Info, "Last used:", "29.06.20")
}