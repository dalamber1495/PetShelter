package com.example.petshelter.tabScreens.announcementTab.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.tabScreens.announcementTab.model.DetailAnimalUiState
import com.example.petshelter.tabScreens.announcementTab.model.LocateData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailAnimalViewModel @Inject constructor(
    val appNavigation: AppNavigation
) : ViewModel() {

    var animalId:String = ""

  private val title = MutableLiveData("")
  private val description = MutableLiveData("")
  private val photo = MutableLiveData<List<Uri?>>()
  private val address = MutableLiveData("")
  private val locatePhoto = MutableLiveData(LocateData())


    val uiState = DetailAnimalUiState(
        title = title,
        description = description,
        photo = photo,
        address = address,
        locatePhoto = locatePhoto
    )

    fun backButtonCallback(){
        appNavigation.navigateTo(AppScreens.PopBackStack)
    }
    fun loadAnimalData(){}


}