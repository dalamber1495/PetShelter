package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.example.petshelter.utils.getBitmapFromVectorDrawable
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*


const val TAG = "Second Step Form"

@Composable
fun SecondStepCreateAnnouncementForm(
    secondStepLocateData: SecondStepLocateData,
    markerPositionCallback: () -> SecondStepLocateData,
    secondStepReadyCallback: (SecondStepLocateData) -> Unit
) {

    val coordinate = LatLng(secondStepLocateData.latPhoto!!, secondStepLocateData.lngPhoto!!)
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(coordinate, 18f)
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBarCreateAnnouncement(backArrowShow = true, textTopBar = "Где вы видели питомца?", {})
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                coordinateMarker = coordinate,
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                }
            )
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
                        val coordinateData =  markerPositionCallback.invoke()
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(
                                coordinateData.latPhoto!!,
                                coordinateData.lngPhoto!!
                            ), 18f
                        )

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
                    clickCallback = {
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

            }
        }


    }


}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    coordinateMarker:LatLng,
    cameraPositionState: CameraPositionState,
    onMapLoaded: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    val petPositionState = rememberMarkerState(position = coordinateMarker)
    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false,
                zoomControlsEnabled = false
            )
        )
    }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val markerImage = getBitmapFromVectorDrawable(LocalContext.current, R.drawable.ic_pet_marker)
//    BitmapFactory.decodeResource(LocalContext.current.resources,R.drawable.ic_pet_marker)
    Log.e(TAG, "GoogleMapView: $markerImage")

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoaded,
        onPOIClick = {
            Log.d(TAG, "POI clicked: ${it.name}")
        }
    ) {
        val markerClick: (Marker) -> Boolean = {
            Log.d(TAG, "${it.title} was clicked")
            cameraPositionState.projection?.let { projection ->
                Log.d(TAG, "The current projection is: $projection")
            }
            false
        }
        MarkerOptions.CONTENTS_FILE_DESCRIPTOR
        Marker(
            state = petPositionState,
            title = "Marker in Animal",
            onClick = markerClick,
            icon = BitmapDescriptorFactory.fromBitmap(markerImage!!)
        )
    }
}

