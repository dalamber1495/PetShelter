package com.example.petshelter.tabScreens.createAnnouncementTab.model

import android.net.Uri
import androidx.lifecycle.LiveData

data class FirstStepUiState(
    val addPhotoBtn:LiveData<Boolean>,
    val showTookPhoto:LiveData<Uri?>
)
