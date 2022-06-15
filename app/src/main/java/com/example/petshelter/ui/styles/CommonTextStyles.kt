package com.example.petshelter.ui.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eql.consts.ui.colors.petShelterBlack
import com.example.petshelter.ui.theme.mulishFont

val authTabsTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 16.sp,
    fontWeight = FontWeight.W700,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)
val mainTabsSelectedTextStyle = TextStyle(
    color = Color.White,
    fontSize = 12.sp,
    fontWeight = FontWeight.W800,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)
val mainTabsUnselectedTextStyle = TextStyle(
    color = Color.White,
    fontSize = 12.sp,
    fontWeight = FontWeight.W400,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)