package com.example.petshelter.authScreens.main.model

import androidx.lifecycle.LiveData

data class ForgetPasswordUiState(
    val validationEmail: LiveData<String?>,
    val validationPassword: LiveData<String?>,
    val validationName: LiveData<String?>,
    val validationRepeatPassword: LiveData<String?>
)
