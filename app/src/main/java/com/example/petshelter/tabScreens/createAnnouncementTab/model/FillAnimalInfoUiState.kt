package com.example.petshelter.tabScreens.createAnnouncementTab.model

import android.net.Uri
import androidx.lifecycle.LiveData

data class FillAnimalInfoUiState(
    val firstStep: LiveData<Boolean>,
    val secondStep: LiveData<Boolean>,
    val thirdStep: LiveData<Boolean>,
    val secondStepLocateData: LiveData<SecondStepLocateData>,
    val animalSelected:LiveData<AnimalCardState>,
    val titleText:LiveData<String>,
    val descriptionText:LiveData<String>,
    val avatarUri: LiveData<Uri>,
    val screenBusy: LiveData<Boolean>,
    val errorMessage: LiveData<String>
)
