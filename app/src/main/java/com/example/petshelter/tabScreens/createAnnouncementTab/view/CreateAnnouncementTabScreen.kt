package com.example.petshelter.tabScreens.createAnnouncementTab.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.FirstStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.SecondStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.ThirdStepCreateAnnouncementForm
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun CreateAnnouncementTabScreen(
    uiState: FillAnimalInfoUiState,
    addPhotoCallback: (Uri?) -> Unit,
    firstStepReadyCallback: () -> Unit,
    secondStepReadyCallback: (SecondStepLocateData) -> Unit,
    markerPositionCallback: (Boolean, (SecondStepLocateData) -> Unit) -> Unit,
    animalSelectedCallback: (AnimalCardState) -> Unit,
    backArrowCallback: () -> Unit,
    defaultValues: () -> Unit,
    setTitleCallback:(String)->Unit,
    setDescriptionCallback:(String)->Unit,
    postAnnouncementCallback:()->Unit,
) {
    val firstStep = uiState.firstStep.observeAsState(initial = false)
    val secondStep = uiState.secondStep.observeAsState(initial = false)
    val thirdStep = uiState.thirdStep.observeAsState(initial = false)
    val avatarUri = uiState.avatarUri.observeAsState()
    val screenIsBusy = uiState.screenBusy.observeAsState(initial = false)
    val secondStepLocateData =
        uiState.secondStepLocateData.observeAsState(initial = SecondStepLocateData())
    val animalCardSelected = uiState.animalSelected.observeAsState(initial = AnimalCardState.Cat)
    val title = uiState.titleText.observeAsState(initial = "")
    val descriptionText = uiState.descriptionText.observeAsState(initial = "")

    LaunchedEffect("CheckStepsState") {
        defaultValues.invoke()
    }

    Column {
        when {
            !firstStep.value && !secondStep.value && !thirdStep.value -> {
                FirstStepCreateAnnouncementForm(
                    screenIsBusy = screenIsBusy.value,
                    avatarUri = avatarUri.value,
                    firstStepReadyCallback = firstStepReadyCallback,
                    addPhotoCallback = addPhotoCallback,
                )
            }
            firstStep.value && !secondStep.value && !thirdStep.value -> {
                SecondStepCreateAnnouncementForm(
                    secondStepLocateData = secondStepLocateData.value,
                    markerPositionCallback = markerPositionCallback,
                    secondStepReadyCallback = secondStepReadyCallback,
                    backArrowCallback = backArrowCallback,
                )
            }
            firstStep.value && secondStep.value && !thirdStep.value -> {
                ThirdStepCreateAnnouncementForm(
                    animalCardSelected.value,
                    animalSelectedCallback,
                    backArrowCallback,
                    title.value,
                    descriptionText.value,
                    setTitleCallback,
                    setDescriptionCallback,
                    postAnnouncementCallback
                )
            }
        }
    }
    if (screenIsBusy.value)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        CircularProgressIndicator(color = petShelterBlue)
    }

}


@ExperimentalMaterialApi
@Preview
@Composable
fun CreateAnnouncementTabScreenPreview() {

    PetShelterTheme {
        CreateAnnouncementTabScreen(
            FillAnimalInfoUiState(
                firstStep = MutableLiveData(false),
                secondStep = MutableLiveData(false),
                thirdStep = MutableLiveData(false),
                avatarUri = MutableLiveData(null),
                screenBusy = MutableLiveData(false),
                errorMessage = MutableLiveData(""),
                secondStepLocateData = MutableLiveData(SecondStepLocateData()),
                animalSelected = MutableLiveData(AnimalCardState.Cat),
                titleText = MutableLiveData(""),
                descriptionText = MutableLiveData("")

            ),
            {},
            {},
            {},
            { i, d -> },
            { SecondStepLocateData() },
            {},
            {},
            {},
            {},
            {},
        )
    }
}