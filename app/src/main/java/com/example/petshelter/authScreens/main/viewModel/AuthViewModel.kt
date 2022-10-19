package com.example.petshelter.authScreens.main.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.model.AuthUiState
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.domain.useCases.AuthUserUseCase
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigation: AppNavigation,
    private val authUserUseCase: AuthUserUseCase,
    private val userDataRepository: UserDataRepository
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
    private val errorMessage = MutableLiveData("")

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
        validationJoinPassword = validationJoinPassword,
        errorMessage = errorMessage
    )

    fun navigateTo(route: AppScreens) {
        appNavigation.navigateTo(route)
    }

    fun registerCallback() {
        if(registerValidation()){
            authUserUseCase.invoke(name = name.value!!,password = registerPassword.value!!, email = registerEmail.value!!).onEach {
                when(it){
                    is Resource.Success ->{
                        screenBusy.postValue(false)
                        userDataRepository.saveLoggedUserTokens(it.data!!)
                        navigateTo(AppScreens.MainAppScreen)
                    }
                    is Resource.Loading ->{screenBusy.postValue(true)}
                    is Resource.Error -> {
                        screenBusy.postValue(false)
                        errorMessage.postValue(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun joinCallback() {
        if(authValidation()){
            email.value?.let { password.value?.let { it1 ->
                authUserUseCase.invoke(it, it1).onEach {
                    when(it){
                        is Resource.Success ->{
                            screenBusy.postValue(false)
                            userDataRepository.saveLoggedUserTokens(it.data!!)
                            navigateTo(AppScreens.MainAppScreen)
                        }
                        is Resource.Loading ->{screenBusy.postValue(true)}
                        is Resource.Error -> {
                            screenBusy.postValue(false)
                            errorMessage.postValue(it.message)
                        }
                    }
                }.launchIn(viewModelScope)
            } }
        }
    }

    private fun registerValidation():Boolean {
        var validation = true
        when (name.value?.isEmpty()) {
            true -> {
                validationName.postValue(UiText.StringResource(R.string.validation_name))
                validation = false
            }
            else -> validationName.postValue(UiText.EmptyString)
        }
        when (Patterns.EMAIL_ADDRESS.matcher(registerEmail.value.toString()).matches()) {
            true -> validationRegisterEmail.postValue(UiText.EmptyString)
            false -> {
                validationRegisterEmail.postValue(UiText.StringResource(R.string.validation_email))
                validation = false
            }
        }
        when (registerPassword.value?.isEmpty()) {
            true -> {
                validationRegisterPassword.postValue(UiText.StringResource(R.string.validation_name))
                validation = false
            }
                else -> validationRegisterPassword.postValue(UiText.EmptyString)
        }
        when (registerPassword.value != repeatPassword.value) {
            true -> {
                validationRepeatPassword.postValue(UiText.StringResource(R.string.validation_repeat_pass))
                validation = false
            }
            else -> validationRepeatPassword.postValue(UiText.EmptyString)
        }
        return validation
    }

    private fun authValidation() :Boolean{
        var validation = true
        when (Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()) {
            true -> validationJoinEmail.postValue(UiText.EmptyString)
            false -> {
                validationJoinEmail.postValue(UiText.StringResource(R.string.validation_email))
                validation = false
            }
        }
        when (password.value?.isEmpty()) {
            true -> {
                validationJoinPassword.postValue(UiText.StringResource(R.string.validation_name))
                validation = false
            }
            else -> validationJoinPassword.postValue(UiText.EmptyString)
        }
        return validation
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