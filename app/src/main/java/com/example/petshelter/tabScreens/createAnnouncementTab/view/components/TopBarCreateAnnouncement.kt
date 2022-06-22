package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
    textTopBar:String? = null,
    iconButtonRight:Int? = null,
    iconButtonCallback:(()->Unit) = {  },
    backArrowCallback:(()->Unit) = { }
){
    TopAppBar(
        modifier = Modifier.fillMaxHeight(0.1f),
        navigationIcon = {
            if (backArrowShow) {
                IconButton(onClick = backArrowCallback) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back Btn"
                    )
                }
            }
        },
        title = {
            Row {
                textTopBar?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = topBarTextStyle,
                        text = it
                    )
                }
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {

                }
            }

        },

        backgroundColor = petShelterBlue,
        contentColor = petShelterWhite,
        actions = {
            iconButtonRight?.let {
                IconButton(onClick = iconButtonCallback) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = it),
                        contentDescription = "Menu Btn",
                        tint = Color.White
                    )
                }
            }
        }
    )
}