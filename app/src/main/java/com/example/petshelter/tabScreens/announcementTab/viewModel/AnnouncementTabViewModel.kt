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
    private val listDogs = MutableLiveData(AnnouncementsListState())
    private val listCats = MutableLiveData(AnnouncementsListState())
    private val listOther = MutableLiveData(AnnouncementsListState())
    private val isRefreshing = MutableLiveData(false)
    private val animalsTabs = MutableLiveData(AnimalsTabItem.All.route)
    private val _detailUiState = mutableStateOf<Announcement?>(null)
    val detailUiState: State<Announcement?> = _detailUiState
    val uiState = AnnouncementTabUiState(
        animalsTabs = animalsTabs,
        animalsState = animalsState,
        listDogs = listDogs,
        listCats = listCats,
        listOther = listOther,
        isRefreshing = isRefreshing
    )

    init {
        initAnnouncement()
    }

    fun initAnnouncement() {
        getAnnouncements("")
        getAnnouncements("dog")
        getAnnouncements("cat")
        getAnnouncements("other")
    }

    fun getAnnouncements(petType: String) {
        getAnnouncementsUseCase(petType).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    when (petType) {
                        "dog" -> listDogs.postValue(AnnouncementsListState(announcements = result.data ?: emptyList()))
                        "cat" -> listCats.postValue(AnnouncementsListState(announcements = result.data ?: emptyList()))
                        "other" -> listOther.postValue(AnnouncementsListState(announcements = result.data ?: emptyList()))
                        else -> animalsState.postValue(AnnouncementsListState(announcements = result.data ?: emptyList()))
                    }
                }
                is Resource.Loading -> {
                    when (petType) {
                        "dog" -> listDogs.postValue(listDogs.value?.copy(isLoading = true))
                        "cat" -> listCats.postValue(listCats.value?.copy(isLoading = true))
                        "other" -> listOther.postValue(listOther.value?.copy(isLoading = true))
                        else -> animalsState.postValue(animalsState.value?.copy(isLoading = true))
                    }
                }
                is Resource.Error -> {
                    when (petType) {
                        "dog" -> listDogs.postValue(AnnouncementsListState(error = result.message ?: "An unexpected error occured"))
                        "cat" -> listCats.postValue(AnnouncementsListState(error = result.message ?: "An unexpected error occured"))
                        "other" -> listOther.postValue(AnnouncementsListState(error = result.message ?: "An unexpected error occured"))
                        else -> animalsState.postValue(AnnouncementsListState(error = result.message ?: "An unexpected error occured"))
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun tabSelectCallback(tab: AnimalsTabItem) {
        animalsTabs.postValue(tab.route)
    }

    fun selectAnnouncementCallback(announcement: Announcement) {
        _detailUiState.value = announcement
    }

    fun popBackStack(navController: NavController) {
        navController.popBackStack()
    }

    fun navigateTo(
        navController: NavController,
        announcementsScreenRoute: AnnouncementsScreenRoute
    ) {
        navController.navigate(announcementsScreenRoute.route, announcementsScreenRoute.options)
    }
}