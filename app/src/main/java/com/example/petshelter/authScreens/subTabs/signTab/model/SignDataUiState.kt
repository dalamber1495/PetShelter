package com.example.petshelter.authScreens.subTabs.signTab.model

import androidx.lifecycle.LiveData

data class SignDataUiState(
    val email:LiveData<String?>,
    val password:LiveData<String?>
)
