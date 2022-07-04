package com.example.petshelter.authScreens.main.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.model.AuthUiState
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigation: AppNavigation
) : ViewModel() {

    private val email = MutableLiveData("")
    private val password = MutableLiveData("")
    private val registerEmail = MutableLiveData("")
    private val registerPassword = MutableLiveData("")
    private val name = MutableLiveData("")
    private val repeatPassword = MutableLiveData("")
    private val screenBusy = MutableLiveData(false)
    private val validationJoinEmail = MutableLiveData<UiText>(UiText.EmptyString)
    private val validationJoinPassword = MutableLiveData<UiText>(UiText.EmptyString)
    private val validationRegisterEmail = MutableLiveData<UiText>(UiText.EmptyString)
    private val validationRegisterPassword = MutableLiveData<UiText>(UiText.EmptyString)
    private val validationName = MutableLiveData<UiText>(UiText.EmptyString)
    private val validationRepeatPassword = MutableLiveData<UiText>(UiText.EmptyString)

    val uiState = AuthUiState(
        email = email,
        password = password,
        registerEmail = registerEmail,
        registerPassword = registerPassword,
        name = name,
        repeatPassword = repeatPassword,
        screenBusy = screenBusy,
        validationRegisterEmail = validationRegisterEmail,
        validationRegisterPassword = validationRegisterPassword,
        validationName = validationName,
        validationRepeatPassword = validationRepeatPassword,
        validationJoinEmail = validationJoinEmail,
        validationJoinPassword = validationJoinPassword
    )

    fun navigateTo(route: AppScreens) {
        appNavigation.navigateTo(route)
    }

    fun registerCallback() {
        registerValidation()
    }

    fun joinCallback() {
        authValidation()
    }

    private fun registerValidation() {
        when (name.value?.isEmpty()) {
            true -> validationName.postValue(UiText.StringResource(R.string.validation_name))
            else -> validationName.postValue(UiText.EmptyString)
        }
        when (Patterns.EMAIL_ADDRESS.matcher(registerEmail.value.toString()).matches()) {
            true -> validationRegisterEmail.postValue(UiText.EmptyString)
            false -> validationRegisterEmail.postValue(UiText.StringResource(R.string.validation_email))
        }
        when (registerPassword.value?.isEmpty()) {
            true -> validationRegisterPassword.postValue(UiText.StringResource(R.string.validation_name))
            else -> validationRegisterPassword.postValue(UiText.EmptyString)
        }
        when (registerPassword.value != repeatPassword.value) {
            true -> validationRepeatPassword.postValue(UiText.StringResource(R.string.validation_repeat_pass))
            else -> validationRepeatPassword.postValue(UiText.EmptyString)
        }
    }

    private fun authValidation() {
        when (Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()) {
            true -> validationJoinEmail.postValue(UiText.EmptyString)
            false -> validationJoinEmail.postValue(UiText.StringResource(R.string.validation_email))
        }
        when (password.value?.isEmpty()) {
            true -> validationJoinPassword.postValue(UiText.StringResource(R.string.validation_name))
            else -> validationJoinPassword.postValue(UiText.EmptyString)
        }
    }

    fun joinEmailCallback(text: String) {
        email.postValue(text)
    }

    fun joinPasswordCallback(text: String) {
        password.postValue(text)
    }

    fun nameCallback(text: String) {
        name.postValue(text)
    }

    fun registerEmailCallback(text: String) {
        registerEmail.postValue(text)
    }

    fun registerPasswordCallback(text: String) {
        registerPassword.postValue(text)
    }

    fun repeatPasswordCallback(text: String) {
        repeatPassword.postValue(text)
    }
}