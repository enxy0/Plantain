package kek.plantain.ui

import androidx.ui.material.Typography
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import androidx.ui.unit.sp
import kek.plantain.R

val firaBold = fontFamily(
    ResourceFont(
        resId = R.font.fira_bold,
        weight = FontWeight.W900,
        style = FontStyle.Normal
    )
)

val fira = fontFamily(
    ResourceFont(
        resId = R.font.fira_regular,
        weight = FontWeight.W700,
        style = FontStyle.Normal
    )
)

val defaultTypography = Typography()
val typography = Typography(
    h6 = defaultTypography.h6.copy(fontFamily = firaBold),
    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = fira),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = fira),
    body1 = defaultTypography.body1.copy(
        fontFamily = fira,
        fontSize = 17.sp
    ),
    body2 = defaultTypography.body2.copy(
        fontFamily = fira,
        fontSize = 17.sp,
        color = grey400
    ),
    button = defaultTypography.button.copy(fontFamily = firaBold),
    caption = defaultTypography.caption.copy(fontFamily = fira),
    overline = defaultTypography.overline.copy(fontFamily = fira)
)