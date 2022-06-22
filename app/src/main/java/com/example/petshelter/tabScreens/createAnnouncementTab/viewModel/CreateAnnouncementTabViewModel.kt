package com.example.petshelter.tabScreens.createAnnouncementTab.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateAnnouncementTabViewModel @Inject constructor(
    val appNavigation: AppNavigation
) : ViewModel() {

    private val screenBusy = MutableLiveData(false)
    private val firstStepReady = MutableLiveData(false)
    private val secondStepReady = MutableLiveData(false)
    private val thirdStepReady = MutableLiveData(false)
    private val animalSelected = MutableLiveData(AnimalCardState.Cat)
    private val titleText = MutableLiveData("")
    private val descriptionText = MutableLiveData("")
    private val secondStepLocateData = MutableLiveData(SecondStepLocateData())
    private val avatarUri = MutableLiveData<Uri>()
    private val errorMessage = MutableLiveData("")

    val uiState = FillAnimalInfoUiState(
        firstStep = firstStepReady,
        secondStep = secondStepReady,
        thirdStep = thirdStepReady,
        animalSelected= animalSelected,
        titleText = titleText,
        descriptionText = descriptionText,
        secondStepLocateData = secondStepLocateData,
        avatarUri = avatarUri,
        screenBusy = screenBusy,
        errorMessage = errorMessage
    )

    fun defaultValuesSelect(
        firstStepFilled: Boolean = false,
        secondStepFilled: Boolean = false,

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

    fun showPhoto(uri: Uri?) {
        avatarUri.postValue(uri)
    }

    fun firstStepReady() {
        firstStepReady.postValue(true)
    }

    fun secondStepReady(secondStepLocalData: SecondStepLocateData) {
        secondStepReady.postValue(true)
    }

    fun markerPositionMove(): SecondStepLocateData =
        secondStepLocateData.value!!

    fun animalSelected(animalCardState: AnimalCardState){
        appNavigation
        animalSelected.postValue(animalCardState)
    }

    private fun firstStepCallback(animalData: FirstStepAddPhotoData): Boolean {
        return false
    }

    private fun secondStepReady(animalData: FirstStepAddPhotoData): Boolean {
        return false
    }
}