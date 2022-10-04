package com.example.petshelter.tabScreens.announcementTab.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.domain.useCases.GetAnnouncementUseCase
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
    val getAnnouncementsUseCase: GetAnnouncementsUseCase,
    val getAnnouncementUseCase: GetAnnouncementUseCase
) : ViewModel() {

    private val _snackbarState = MutableStateFlow<Int?>(null)
    val snackbarState: StateFlow<Int?> = _snackbarState.asStateFlow()
    private val animalsState = MutableLiveData(AnnouncementsListState())
    private val listDogs = MutableLiveData(AnnouncementsListState())
    private val listCats = MutableLiveData(AnnouncementsListState())
    private val listOther = MutableLiveData(AnnouncementsListState())
    private val isRefreshing = MutableLiveData(false)
    private val animalsTabs = MutableLiveData(AnimalsTabItem.All.route)
    private val _detailUiState = mutableStateOf(AnnouncementsListState())
    val detailUiState: State<AnnouncementsListState> = _detailUiState
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
        _detailUiState.value = _detailUiState.value?.copy(announcements = listOf(announcement))
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


    fun openDetail(id: Int,navController: NavController) {
        navController.navigate(AnnouncementsScreenRoute.DetailAnimalRoute.route)
        getAnnouncement(id)
    }

    private fun getAnnouncement(id: Int) {
        getAnnouncementUseCase(id.toString()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _detailUiState.value =
                        result.data?.let { AnnouncementsListState(announcements = listOf(result.data)) }!!
                    _snackbarState.value = id
                }
                is Resource.Loading -> {
                    _detailUiState.value =
                        AnnouncementsListState(
                            isLoading = true
                        )                }
                is Resource.Error -> {
                    _detailUiState.value =
                        AnnouncementsListState(
                            error = result.message ?: "An unexpected error occured"
                        )

                }
            }
        }.launchIn(viewModelScope)
    }
}