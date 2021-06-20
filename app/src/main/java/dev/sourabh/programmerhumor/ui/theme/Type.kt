package dev.sourabh.programmerhumor.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.sourabh.programmerhumor.R

@Suppress("SpellCheckingInspection")
val FiraCodeMono = FontFamily(
    Font(R.font.firacode_regular, FontWeight.Normal),
    Font(R.font.firacode_bold, FontWeight.Bold)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

val Typography = Typography(
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = FiraCodeMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FiraCodeMono,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    button = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FiraCodeMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)