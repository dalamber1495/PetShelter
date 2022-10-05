package com.example.petshelter.tabScreens.announcementTab.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.domain.model.AnnouncementsListState
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.ui.styles.dialogTextStyle
import kotlin.math.roundToInt

@Composable
fun DetailPhotoScreen(
    popBackStack: (NavController) -> Unit,
    navController: NavController,
    selectedAnnouncementState: State<AnnouncementsListState>,

    ) {

//    var scale by remember { mutableStateOf(1f) }
//    var rotation by remember { mutableStateOf(0f) }
//    var offset by remember { mutableStateOf(Offset.Zero) }
//    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
//        scale *= zoomChange
//        rotation += rotationChange
//        offset += offsetChange
//    }
//    val offsetX = remember { mutableStateOf(0f) }
//    val offsetY = remember { mutableStateOf(0f) }
//    var height by remember { mutableStateOf(0f) }
    Scaffold(
        topBar = {
            TopBarCreateAnnouncement(
                backArrowShow = true,
                textTopBar = selectedAnnouncementState.value.announcements.first().title,
                backArrowCallback = { popBackStack.invoke(navController) }
            )
        },
    ) {
        Column(
            modifier = Modifier
//                .onSizeChanged { height = it.height.toFloat() }
                .padding(it),
//                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
//                .pointerInput(Unit) {
//                    detectTransformGestures { centroid, pan, zoom, rotation ->
//                        scale *= zoom
//                    }
//                    detectVerticalDragGestures { _, dragAmount ->
//                        val originalY = offsetY.value
//                        val newValue = (originalY + dragAmount).coerceIn(0f, height - 50.dp.toPx())
//                        offsetY.value = newValue
//                    }
//                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .fillMaxSize(),
//                    .graphicsLayer(
//                        scaleX = maxOf(.5f, minOf(3f, scale)),
//                        scaleY = maxOf(.5f, minOf(3f, scale)),
//                        translationX = offset.x,
//                        translationY = offset.y
//                    )
//                    .transformable(state = state),
                painter = rememberAsyncImagePainter(model = selectedAnnouncementState.value.announcements.first().imageUrl),
                contentDescription = null
            )

        }
    }

}

@Preview
@Composable
fun DetailPhotoScreenPreview() {
    val state = remember {
        mutableStateOf(AnnouncementsListState())
    }
    DetailPhotoScreen(
        popBackStack = {},
        navController = rememberNavController(),
        selectedAnnouncementState = state
    )
}