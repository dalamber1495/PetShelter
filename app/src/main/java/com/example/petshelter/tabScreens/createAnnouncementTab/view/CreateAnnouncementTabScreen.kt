package com.example.petshelter.tabScreens.createAnnouncementTab.view

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
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.FirstStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.SecondStepCreateAnnouncementForm
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun CreateAnnouncementTabScreen(
    uiState: FillAnimalInfoUiState,
    checkStepsState: () -> Unit,
    addPhotoCallback: (FirstStepAddPhotoData) -> Unit,
    firstStepReadyCallback: () -> Unit
) {
    val firstStep = uiState.firstStep.observeAsState(initial = false)
    val secondStep = uiState.secondStep.observeAsState(initial = false)
    val thirdStep = uiState.thirdStep.observeAsState(initial = false)
    val avatarUri = uiState.avatarUri.observeAsState()
    val screenIsBusy = uiState.screenBusy.observeAsState(initial = false)

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

                )
            }
            firstStep.value && secondStep.value && !thirdStep.value -> {

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
                errorMessage = MutableLiveData("")
            ),
            {},
            {},
            {}
        )
    }
}