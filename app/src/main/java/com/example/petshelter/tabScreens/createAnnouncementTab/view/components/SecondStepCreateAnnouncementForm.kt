package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import android.Manifest
import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.common.composables.GoogleMapView
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.example.petshelter.utils.common.PermissionDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*


const val TAG = "Second Step Form"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SecondStepCreateAnnouncementForm(
    secondStepLocateData: SecondStepLocateData,
    markerPositionCallback: () -> Unit,
    secondStepReadyCallback: (SecondStepLocateData) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val coordinate = LatLng(secondStepLocateData.latPhoto!!, secondStepLocateData.lngPhoto!!)
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(coordinate, 18f)

    val permissionStates = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }
    val permissionDialog = remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val eventObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionStates.permissions.forEach {
                    when (it.permission) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            when {
                                it.hasPermission -> {
                                    markerPositionCallback.invoke()
                                }
                                it.shouldShowRationale -> {
                                    permissionStates.launchMultiplePermissionRequest()
                                }
                                !it.hasPermission && !it.shouldShowRationale -> {
                                    permissionDialog.value = true
                                }

                            }
                        }
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(eventObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(eventObserver)
        }
    })
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBarCreateAnnouncement(backArrowShow = true, textTopBar = "Где вы видели питомца?")
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 62.dp)
                        .size(62.dp),
                    painter = painterResource(id = R.drawable.ic_pet_marker),
                    contentDescription = null
                )
            }
            if (!isMapLoaded) {
                androidx.compose.animation.AnimatedVisibility(
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {

                PetShelterBtn(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(top = 16.dp, end = 16.dp),
                    image = R.drawable.ic_location_on,
                    imageSize = 60.dp,
                    clickCallback = {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            coordinate, 18f
                        )
                        permissionStates.permissions.forEach {
                            when (it.permission) {
                                Manifest.permission.ACCESS_FINE_LOCATION -> {
                                    when {
                                        it.hasPermission -> {
                                            markerPositionCallback.invoke()
                                        }
                                        it.shouldShowRationale -> {
                                            permissionStates.launchMultiplePermissionRequest()
                                        }
                                        !it.hasPermission && !it.shouldShowRationale -> {
                                            permissionDialog.value = true
                                        }

                                    }
                                }
                            }
                        }

                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PetShelterBtn(
                    modifier = Modifier
                        .width(205.dp)
                        .height(56.dp),
                    text = "Подтвердить",
                    image = R.drawable.ic_done,
                    clickCallback = { secondStepReadyCallback.invoke(SecondStepLocateData()) }
                )
                Spacer(modifier = Modifier.height(24.dp))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PermissionDialog(
                    isDisplayed = permissionDialog.value,
                    toggleDialogDisplayCallback = { permissionDialog.value = false }) {

                }
            }
        }
    }
}

