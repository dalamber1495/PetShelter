package com.example.petshelter.tabScreens.createAnnouncementTab.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FillAnimalInfoUiState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstStepViewModel @Inject constructor(
    val appNavigation: AppNavigation
): ViewModel(){

    val showTookPhoto = MutableLiveData<Uri?>(null)
    val addPhotoBtn = MutableLiveData(false)

    val uiState = FirstStepUiState(
        addPhotoBtn = addPhotoBtn,
        showTookPhoto = showTookPhoto
    )

    fun showPhoto(firstStepData:FirstStepAddPhotoData){
        showTookPhoto.postValue(firstStepData.photoUri)
    }
}