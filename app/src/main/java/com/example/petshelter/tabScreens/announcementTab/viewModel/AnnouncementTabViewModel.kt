package com.example.petshelter.tabScreens.announcementTab.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.domain.useCases.GetAnnouncementsUseCase
import com.example.petshelter.navigation.AppNavigation
import com.example.petshelter.tabScreens.announcementTab.common.AnimalsTabItem
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementTabUiState
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnnouncementTabViewModel @Inject constructor(
    val getAnnouncementsUseCase: GetAnnouncementsUseCase
) : ViewModel() {
    private val animalsState = MutableLiveData(AnnouncementsListState())
    private val animalsTabs = MutableLiveData(AnimalsTabItem.All.route)
    private val _detailUiState = mutableStateOf<Announcement?>(null)
    val detailUiState:State<Announcement?> = _detailUiState
    val uiState = AnnouncementTabUiState(
        animalsTabs = animalsTabs,
        animalsState = animalsState
    )


    fun getAnnouncements(petType:String) {
        getAnnouncementsUseCase(petType).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    animalsState.postValue(
                        AnnouncementsListState(
                            announcements = result.data ?: emptyList()
                        )
                    )
                }
                is Resource.Loading -> {
                    animalsState.postValue(AnnouncementsListState(isLoading = true))
                }
                is Resource.Error -> {
                    animalsState.postValue(
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectAnnouncementCallback(index:Int){
        _detailUiState.value = animalsState.value?.announcements?.get(index)
    }

    fun popBackStack(navController:NavController){
        navController.popBackStack()
    }

    fun navigateTo(navController:NavController,announcementsScreenRoute: AnnouncementsScreenRoute){
        navController.navigate(announcementsScreenRoute.route,announcementsScreenRoute.options)
    }
}