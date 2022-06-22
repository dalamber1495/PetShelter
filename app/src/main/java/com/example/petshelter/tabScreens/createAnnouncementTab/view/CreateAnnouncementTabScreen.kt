package com.example.petshelter.tabScreens.createAnnouncementTab.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.FirstStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.SecondStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.ThirdStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun CreateAnnouncementTabScreen(
    uiState: FillAnimalInfoUiState,
    checkStepsState: () -> Unit,
    addPhotoCallback: (Uri?) -> Unit,
    firstStepReadyCallback: () -> Unit,
    secondStepReadyCallback: (SecondStepLocateData) -> Unit,
    markerPositionCallback: () -> SecondStepLocateData,
    animalSelectedCallback:(AnimalCardState)->Unit

) {
    val firstStep = uiState.firstStep.observeAsState(initial = false)
    val secondStep = uiState.secondStep.observeAsState(initial = false)
    val thirdStep = uiState.thirdStep.observeAsState(initial = false)
    val avatarUri = uiState.avatarUri.observeAsState()
    val screenIsBusy = uiState.screenBusy.observeAsState(initial = false)
    val secondStepLocateData =
        uiState.secondStepLocateData.observeAsState(initial = SecondStepLocateData())
    val animalCardSelected = uiState.animalSelected.observeAsState(initial = AnimalCardState.Cat)

//    LaunchedEffect("CheckStepsState") {
//        checkStepsState.invoke()
//    }


    Column {
        when {
            !firstStep.value && !secondStep.value && !thirdStep.value -> {
                FirstStepCreateAnnouncementForm(
                    screenIsBusy = screenIsBusy.value,
                    avatarUri = avatarUri.value,
                    firstStepReadyCallback = firstStepReadyCallback,
                    addPhotoCallback = addPhotoCallback
                )
            }
            firstStep.value && !secondStep.value && !thirdStep.value -> {
                SecondStepCreateAnnouncementForm(
                    secondStepLocateData = secondStepLocateData.value,
                    markerPositionCallback = markerPositionCallback,
                    secondStepReadyCallback = secondStepReadyCallback
                )
            }
            firstStep.value && secondStep.value && !thirdStep.value -> {
                ThirdStepCreateAnnouncementForm(animalCardSelected.value,animalSelectedCallback)
            }
        }
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
            {},
            { SecondStepLocateData() },
            {}
        )
    }
}