package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SecondStepCreateAnnouncementForm(){


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        TopBarCreateAnnouncement(backArrowShow = true, textTopBar = "Где вы видели питомца?",{})

    }


}