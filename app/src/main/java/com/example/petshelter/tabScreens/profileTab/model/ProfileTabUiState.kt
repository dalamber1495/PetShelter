package com.example.petshelter.tabScreens.profileTab.model

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData

data class ProfileTabUiState(
val nameAndSurname: LiveData<String>,
val avatarUri: LiveData<Uri?>,
val screenBusy: LiveData<Boolean>,
val errorMessage: LiveData<String>

)
