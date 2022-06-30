package com.example.petshelter.common.composables

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.petshelter.R
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TAG
import com.example.petshelter.utils.getBitmapFromVectorDrawable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.*

@Composable
fun GoogleMapView(
    modifier: Modifier,
    coordinateMarker: LatLng,
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