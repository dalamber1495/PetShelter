package com.example.petshelter.authScreens.subTabs.registerTab.model

import androidx.lifecycle.LiveData

data class RegisterDataUiState(
    val name:LiveData<String?>,
    val email:LiveData<String?>,
    val password:LiveData<String?>,
    val repeatPassword:LiveData<String?>
)
