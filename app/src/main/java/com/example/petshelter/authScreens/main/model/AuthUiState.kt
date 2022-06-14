package com.example.petshelter.authScreens.main.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.SharedFlow

data class AuthUiState (
    val email: LiveData<String>,
    val password: LiveData<String>,
    val name: SharedFlow<String>,
    val repeatPassword: LiveData<Boolean>,
    val screenBusy: LiveData<Boolean>
)