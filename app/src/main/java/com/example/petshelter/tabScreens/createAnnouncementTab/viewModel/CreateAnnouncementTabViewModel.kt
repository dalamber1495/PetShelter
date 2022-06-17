package com.example.petshelter.tabScreens.createAnnouncementTab.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateAnnouncementTabViewModel @Inject constructor(
    val appNavigation: AppNavigation
):ViewModel(){

    private val screenBusy = MutableLiveData(false)
    private val firstStepReady = MutableLiveData(false)
    private val secondStepReady = MutableLiveData(false)
    private val thirdStepReady = MutableLiveData(false)
    private val avatarUri = MutableLiveData<Uri>()
    private val errorMessage = MutableLiveData("")

    val uiState = FillAnimalInfoUiState(
        firstStep = firstStepReady,
        secondStep = secondStepReady,
        thirdStep = thirdStepReady,
        avatarUri = avatarUri,
        screenBusy = screenBusy,
        errorMessage = errorMessage
    )

    fun defaultValuesSelect(
        firstStepFilled: Boolean = false,
        secondStepFilled:Boolean = false,

        ) {
        screenBusy.postValue(false)
        firstStepReady.postValue(firstStepFilled)
        secondStepReady.postValue(secondStepFilled)
        thirdStepReady.postValue(false)
        errorMessage.postValue("")
    }

    fun checkPersonalInfoState() {
        try {
            screenBusy.postValue(true)

//            val personalData = userDataRepository.getUserPersonalData()
//            val firstStepComplete = firstStepCallback(personalData)
//            val secondStepComplete = secondStepReady(personalData)
            val thirdStepComplete = false

//            firstStepReady.postValue(firstStepComplete)
//            secondStepReady.postValue(secondStepComplete)
            thirdStepReady.postValue(thirdStepComplete)
        } catch (exception: Exception) {
            errorMessage.postValue("Error : ${exception.message}")
        } finally {
            screenBusy.postValue(false)
        }
    }

    fun backArrowCallback() {
        when {
            firstStepReady.value!! && !secondStepReady.value!! && !thirdStepReady.value!! -> {
                firstStepReady.postValue(false)
            }
            firstStepReady.value!! && secondStepReady.value!! && !thirdStepReady.value!! -> {
                secondStepReady.postValue(false)
            }
            else -> {

            }
        }
    }

    fun showPhoto(firstStepData:FirstStepAddPhotoData){
        avatarUri.postValue(firstStepData.photoUri)
    }
    fun firstStepReady(){
        firstStepReady.postValue(true)
    }

    private fun firstStepCallback(animalData: FirstStepAddPhotoData): Boolean {
        return false
    }

    private fun secondStepReady(animalData: FirstStepAddPhotoData): Boolean {
        return false
    }
}