package com.example.petshelter.tabScreens.announcementTab.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.domain.useCases.GetAnnouncementsUseCase
import com.example.petshelter.tabScreens.announcementTab.common.AnimalsTabItem
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementTabUiState
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnnouncementTabViewModel @Inject constructor(
    val getAnnouncementsUseCase: GetAnnouncementsUseCase
) : ViewModel() {

    private val _snackbarState = MutableStateFlow<Int?>(null)
    val snackbarState: StateFlow<Int?> = _snackbarState.asStateFlow()
    private val _detailId = MutableStateFlow<Int?>(null)
    val detailId: StateFlow<Int?> = _detailId.asStateFlow()
    private val animalsState = MutableLiveData(AnnouncementsListState())
    private val listDogs = MutableLiveData(AnnouncementsListState())
    private val listCats = MutableLiveData(AnnouncementsListState())
    private lateinit var navController: NavController
    private val listOther = MutableLiveData(AnnouncementsListState())
    private val isRefreshing = MutableLiveData(false)
    private val animalsTabs = MutableLiveData(AnimalsTabItem.All.route)
    private val _detailUiState = mutableStateOf<AnnouncementState?>(null)
    val detailUiState: State<AnnouncementState?> = _detailUiState
    val uiState = AnnouncementTabUiState(
        animalsTabs = animalsTabs,
        animalsState = animalsState,
        listDogs = listDogs,
        listCats = listCats,
        listOther = listOther,
        isRefreshing = isRefreshing
    )

    init {
        getAnnouncements()
    }


    fun getAnnouncements() {
        getAnnouncementsUseCase("").onEach { result ->
            when (result) {
                is Resource.Success -> {
                    animalsState.postValue(result.data
                        ?.let { AnnouncementsListState(announcements = it) })
                    listDogs.postValue(result.data?.filter { it.petType == "dog" }
                        ?.let { AnnouncementsListState(announcements = it) })
                    listCats.postValue(result.data?.filter { it.petType == "cat" }
                        ?.let { AnnouncementsListState(announcements = it) })
                    listOther.postValue(result.data?.filter { it.petType == "other" }
                        ?.let { AnnouncementsListState(announcements = it) })
                    detailId.value?.let { id ->
                        _detailUiState.value =
                            result.data?.find { it.id == id.toString() }
                        Log.e("TAG", "getAnnouncements2: ${id} ")
                        _snackbarState.value = id
                        navController.navigate(AnnouncementsScreenRoute.DetailAnimalRoute.route)
                        _detailId.value = null
                    }
                }
                is Resource.Loading -> {
                    listDogs.postValue(listDogs.value?.copy(isLoading = true))
                    listCats.postValue(listCats.value?.copy(isLoading = true))
                    listOther.postValue(listOther.value?.copy(isLoading = true))
                    animalsState.postValue(animalsState.value?.copy(isLoading = true))
                }
                is Resource.Error -> {
                    listDogs.postValue(
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                    listCats.postValue(
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                    listOther.postValue(
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                    animalsState.postValue(
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun tabSelectCallback(tab: AnimalsTabItem) {
        animalsTabs.postValue(tab.route)
    }

    fun selectAnnouncementCallback(announcement: AnnouncementState) {
        _detailUiState.value = announcement
    }

    suspend fun snackBarOffCallback() {
        _snackbarState.emit(null)
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

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun openDetail(id: Int?) {
        id?.let {
            _detailId.value = it
        }
    }
}