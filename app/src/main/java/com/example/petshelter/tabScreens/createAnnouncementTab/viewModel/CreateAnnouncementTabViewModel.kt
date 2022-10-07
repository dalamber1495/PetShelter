package com.example.petshelter.tabScreens.createAnnouncementTab.viewModel

import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.GeoPosition
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.domain.useCases.PostAnnouncementUseCase
import com.example.petshelter.geo.LocationLiveData
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.createAnnouncementTab.model.AnimalCardState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAnnouncementTabViewModel @Inject constructor(
    val appNavigation: AppNavigation,
    val locationLiveData: LocationLiveData,
    val postAnnouncementUseCase: PostAnnouncementUseCase,
    val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val screenBusy = MutableLiveData(false)
    private val firstStepReady = MutableLiveData(false)
    private val secondStepReady = MutableLiveData(false)
    private val thirdStepReady = MutableLiveData(false)
    private val animalSelected = MutableLiveData(AnimalCardState.Cat)
    private val titleText = MutableLiveData("")
    private val descriptionText = MutableLiveData("")
    private val secondStepLocateData = MutableLiveData(SecondStepLocateData())
    private val avatarUri = MutableLiveData<Uri?>()
    private val errorMessage = MutableLiveData("")
    private lateinit var openDetailTab: (String)->Unit

    val uiState = FillAnimalInfoUiState(
        firstStep = firstStepReady,
        secondStep = secondStepReady,
        thirdStep = thirdStepReady,
        animalSelected = animalSelected,
        titleText = titleText,
        descriptionText = descriptionText,
        secondStepLocateData = secondStepLocateData,
        avatarUri = avatarUri,
        screenBusy = screenBusy,
        errorMessage = errorMessage
    )

    fun setTabNavController(openDetailTab: (String)->Unit){
        defaultValuesSelect()
        this.openDetailTab = openDetailTab
    }
    fun defaultValuesSelect(
    ) {
        screenBusy.postValue(false)
        userDataRepository.getFirstScreenPhotoUri()?.let { it ->
            avatarUri.postValue(it)
            firstStepReady.postValue(true)
            if(userDataRepository.getSecondScreenLocate() == null){
                viewModelScope.launch{
                    val locateData = locationLiveData.getCurrentPosition()
                    secondStepLocateData.postValue(SecondStepLocateData(locateData.latPhoto,locateData.lngPhoto))
                }
            }
            userDataRepository.getSecondScreenLocate()?.let { locate ->
                secondStepLocateData.postValue(locate)
                secondStepReady.postValue(true)
            }
        }
        Log.e("STATE", "defaultValuesSelect: ${secondStepReady.value} ${firstStepReady.value}", )
    }

    fun setTitleText(title: String) {
        titleText.postValue(title)
    }

    fun setDescriptionText(description: String) {
        descriptionText.postValue(description)
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
        userDataRepository.saveFirstScreenPhotoUri(uri = avatarUri.value!!)
        firstStepReady.postValue(true)

    }

    fun secondStepReady(secondStepLocalData: SecondStepLocateData) {
        userDataRepository.saveSecondScreenLocate(secondStepLocalData)
        secondStepLocateData.postValue(secondStepLocalData)
        secondStepReady.postValue(true)
    }

    fun markerPositionMove(myPosition: Boolean, move: (SecondStepLocateData) -> Unit) {
        viewModelScope.launch {
            val locateData = locationLiveData.getCurrentPosition()
            if (!myPosition) {
                secondStepLocateData.postValue(
                    userDataRepository.getSecondScreenLocate()
                        ?: locateData
                )
            } else {
                move.invoke(locateData)
            }
        }
    }

    fun animalSelected(animalCardState: AnimalCardState) {
        appNavigation
        animalSelected.postValue(animalCardState)
    }

    fun postAnnouncementBtn() {
        postAnnouncementUseCase.invoke(
            Announcement(
                description = descriptionText.value ?: "description",
                geoPosition = GeoPosition(
                    secondStepLocateData.value?.latPhoto!!,
                    secondStepLocateData.value?.lngPhoto!!
                ),
                id = "",
                imageUrl = avatarUri.value,
                petType = animalSelected.value?.animal!!,
                title = titleText.value?:"title"
            )
        ).onEach {  result ->
            when(result){
                is Resource.Success ->{
                    screenBusy.postValue(false)
                    userDataRepository.saveFirstScreenPhotoUri(null)
                    userDataRepository.saveSecondScreenLocate(null)
                    result.data?.id?.let { openDetailTab.invoke(it) }
                }
                is Resource.Loading ->{
                    screenBusy.postValue(true)
                }
                is Resource.Error ->{
                    screenBusy.postValue(false)
                }
            }

        }.launchIn(viewModelScope)
    }
}
