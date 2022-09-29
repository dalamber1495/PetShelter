package com.example.petshelter.tabScreens.profileTab.model

import android.net.Uri
import androidx.lifecycle.LiveData

data class ProfileTabUiState(
val nameAndSurname: LiveData<String>,
val avatarUri: LiveData<Uri?>,
val screenBusy: LiveData<Boolean>,
val errorMessage: LiveData<String>,

)
