package com.example.petshelter.tabScreens.announcementTab.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.petshelter.common.composables.GoogleMapView
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun MapLocationScreen(
    selectedAnnouncementState: State<AnnouncementState?>,
    navController: NavController,
    popBackStack: (NavController) -> Unit
){

    val coordinate = LatLng(selectedAnnouncementState.value?.geoPosition?.lat!!, selectedAnnouncementState.value?.geoPosition?.lng!!)
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(coordinate, 18f)
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

    Scaffold(
        topBar = {
            TopBarCreateAnnouncement(
                backArrowShow = true,
                textTopBar = selectedAnnouncementState.value?.title,
                backArrowCallback = { popBackStack.invoke(navController) }
            )
        }
    ){
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                coordinateMarker = listOf(coordinate),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                }
            )
            if (!isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}
