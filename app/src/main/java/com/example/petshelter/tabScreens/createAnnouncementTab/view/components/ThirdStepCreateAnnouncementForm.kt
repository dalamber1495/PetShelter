package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import androidx.compose.foundation.layout.*
import com.example.petshelter.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petshelter.authScreens.main.components.FormField
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun ThirdStepCreateAnnouncementForm(
    animalCardState: AnimalCardState,
    animalSelected:(AnimalCardState)->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBarCreateAnnouncement(backArrowShow = true, textTopBar = "Опишите питомца")
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimalCard(
                selected = animalCardState == AnimalCardState.Cat,
                image = R.drawable.ic_cat_card_selected ,
                text = "Кошка",
                onClick = {animalSelected.invoke(AnimalCardState.Cat)}
            )
            AnimalCard(
                selected = animalCardState == AnimalCardState.Dog,
                image = R.drawable.ic_dog_card_selected,
                text = "Собака",
                onClick = {animalSelected.invoke(AnimalCardState.Dog)}
            )
            AnimalCard(
                selected = animalCardState == AnimalCardState.Other,
                image =  R.drawable.ic_other_card_selected,
                text = "Другие",
                onClick = {animalSelected.invoke(AnimalCardState.Other)}
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        FormField(modifier = Modifier
            .height(56.dp), placeHolder = "Название объявления") {
        }
        Spacer(modifier = Modifier.height(32.dp))
        FormField(modifier = Modifier
            .fillMaxHeight(0.6f), placeHolder = "Описание питомца") {
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PetShelterBtn(
                modifier = Modifier
                    .height(56.dp)
                    .wrapContentWidth(),
                text = "Разместить объявление",
                image = R.drawable.ic_done,
                clickCallback = {}
            )
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Preview
@Composable
fun ThirdStepCreateAnnouncementFormPreview() {
    PetShelterTheme {
        ThirdStepCreateAnnouncementForm(AnimalCardState.Cat,{})
    }
}