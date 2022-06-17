package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.consts.topBarTextStyle

@Composable
fun TopBarCreateAnnouncement(
    backArrowShow:Boolean,
    textTopBar:String?,
    backArrowCallback:()->Unit
){
    TopAppBar(
        modifier = Modifier.fillMaxHeight(0.1f),
        navigationIcon = {
            if (backArrowShow) {
                IconButton(onClick = backArrowCallback) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Menu Btn"
                    )
                }
            }
        },
        title = {
            Text(modifier  = Modifier
                .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = topBarTextStyle,
                text = textTopBar!!
            )
        },
        backgroundColor = petShelterBlue,
        contentColor = petShelterWhite,
    )
}