package com.example.petshelter.tabScreens.createAnnouncementTab.model

import android.net.Uri
import androidx.lifecycle.LiveData

data class FillAnimalInfoUiState(
    val firstStep: LiveData<Boolean>,
    val secondStep: LiveData<Boolean>,
    val thirdStep: LiveData<Boolean>,
    val secondStepLocateData: LiveData<SecondStepLocateData>,
    val avatarUri: LiveData<Uri>,
    val screenBusy: LiveData<Boolean>,
    val errorMessage: LiveData<String>
)
