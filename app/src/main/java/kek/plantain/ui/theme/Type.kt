package kek.plantain.ui.theme

import androidx.ui.material.Typography
import androidx.ui.unit.sp

val defaultTypography = Typography()
val typography = Typography(
    body1 = defaultTypography.body1.copy(
        fontSize = 17.sp
    ),
    body2 = defaultTypography.body2.copy(
        fontSize = 17.sp,
        color = grey400
    )
)