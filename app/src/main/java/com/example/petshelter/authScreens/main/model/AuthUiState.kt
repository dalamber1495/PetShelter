package com.example.petshelter.authScreens.main.model

import androidx.lifecycle.LiveData
import com.example.petshelter.utils.UiText
import kotlinx.coroutines.flow.SharedFlow

data class AuthUiState (
    val email: LiveData<String>,
    val password: LiveData<String>,
    val registerEmail: LiveData<String>,
    val registerPassword: LiveData<String>,
    val name: LiveData<String>,
    val repeatPassword: LiveData<String>,
    val screenBusy: LiveData<Boolean>,
    val validationRegisterEmail:LiveData<UiText>,
    val validationRegisterPassword:LiveData<UiText>,
    val validationName:LiveData<UiText>,
    val validationRepeatPassword:LiveData<UiText>,
    val validationJoinEmail:LiveData<UiText>,
    val validationJoinPassword:LiveData<UiText>

    )