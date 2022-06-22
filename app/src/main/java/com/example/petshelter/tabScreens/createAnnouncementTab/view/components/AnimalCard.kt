package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.backgroundTopColor
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterOrange
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.ui.styles.cardSelectedTextStyle
import com.example.petshelter.ui.styles.cardUnselectedTextStyle
import com.example.petshelter.ui.theme.Shapes

@Composable
fun AnimalCard(
    modifier: Modifier = Modifier,
    selected:Boolean = false,
    image:Int,
    text:String,
    onClick:()->Unit

){
    Card (
        modifier = Modifier
            .height(96.dp)
            .width(96.dp)
            .clickable (interactionSource = MutableInteractionSource(), indication = null) { onClick.invoke() },
        elevation = if(selected) 0.dp else 6.dp,
        backgroundColor = if (selected) petShelterOrange else petShelterWhite,
        shape = RoundedCornerShape(8.dp)
            ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Icon(
                modifier = Modifier.height(46.dp),
                painter = painterResource(id = image),
                contentDescription = "logo_button",
                tint = if (selected) Color.White else petShelterBlue
            )
            Text(text = text, style = if(selected) cardSelectedTextStyle else cardUnselectedTextStyle)
        }
    }
}