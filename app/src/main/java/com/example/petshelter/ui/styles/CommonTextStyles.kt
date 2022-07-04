package com.example.petshelter.ui.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.eql.consts.ui.colors.petShelterBlack
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.validationErrorColor
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
val cardSelectedTextStyle = TextStyle(
    color = Color.White,
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)
val cardUnselectedTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)

val profileTextStyle = TextStyle(
    color = Color.Black,
    fontSize = 24.sp,
    fontWeight = FontWeight.W700,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)
val dialogTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)
val dialogBtnTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 16.sp,
    fontWeight = FontWeight.W600,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont
)

val titleAnnounceTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 22.sp,
    fontWeight = FontWeight.W700,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont,
    textAlign = TextAlign.Left
)
val descriptionAnnounceTextStyle = TextStyle(
    color = petShelterBlack,
    fontSize = 12.sp,
    fontWeight = FontWeight.W400,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont,
    textAlign = TextAlign.Left,
)
val validationTextStyle = TextStyle(
    color = validationErrorColor,
    fontSize = 12.sp,
    fontWeight = FontWeight.W400,
    fontStyle = FontStyle.Normal,
    fontFamily = mulishFont,
    textAlign = TextAlign.Left,
)