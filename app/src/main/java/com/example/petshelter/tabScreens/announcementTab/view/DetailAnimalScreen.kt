package com.example.petshelter.tabScreens.announcementTab.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterOrange
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.authScreens.main.consts.btnTextStyle
import com.example.petshelter.data.remote.dto.GeoPosition
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.tabScreens.announcementTab.model.LocateData
import com.example.petshelter.tabScreens.announcementTab.navigation.routeObject.AnnouncementsScreenRoute
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.tabScreens.mainScreen.consts.ultraLightGray
import com.example.petshelter.ui.styles.dialogTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun DetailAnimalScreen(
    selectedAnnouncementState: State<Announcement?>,
    navController: NavController,
    navigateCallback: (NavController, AnnouncementsScreenRoute) -> Unit,
    popBackStack: (NavController) -> Unit,
    snackbarState: StateFlow<Int?>,
    snackBarOffCallback: suspend ()->Unit
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = "id") {
        snackbarState.onEach {
            it?.let {
                val result = snackbarHostState.showSnackbar(
                    message = "Объявление опубликовано",
                    duration = SnackbarDuration.Short
                )
                when(result){
                    SnackbarResult.ActionPerformed ->{snackBarOffCallback.invoke()}
                    SnackbarResult.Dismissed -> {snackBarOffCallback.invoke()}
                }
            }
        }.collect()
    }
    Box {
        Scaffold(
            topBar = {
                TopBarCreateAnnouncement(
                    backArrowShow = true,
                    textTopBar = selectedAnnouncementState.value?.title,
                    backArrowCallback = { popBackStack.invoke(navController) }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(
                        state = scrollState,
                    )
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                AnimalPhoto(
                    isPhotoBusy = false,
                    photoUri = selectedAnnouncementState.value?.imageUrl
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pet_marker),
                        contentDescription = "pet marker"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .padding(horizontal = 2.dp),
                        maxLines = 2,
                        text = selectedAnnouncementState.value?.geoPosition?.lat.toString() + " " + selectedAnnouncementState.value?.geoPosition?.lng.toString()
                    )
                    PetShelterBtn(
                        modifier = Modifier
                            .width(136.dp)
                            .height(48.dp),
                        text = "На карте",
                        clickCallback = {
                            navigateCallback.invoke(
                                navController,
                                AnnouncementsScreenRoute.MapLocateRoute
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = selectedAnnouncementState.value?.description ?: "",
                        style = dialogTextStyle
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxHeight(0.09f)
                    .padding(top = 8.dp, start = 10.dp, end = 10.dp)
            ) { data ->
                Snackbar(
                    backgroundColor = petShelterOrange,
                    contentColor = petShelterWhite,
                    action = {
                        IconButton(onClick = { data.performAction() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = petShelterWhite
                            )
                        }
                    }
                ) {
                    Text(text = data.message, style = btnTextStyle)
                }
            }
        }
    }

}

@Composable
fun AnimalPhoto(
    isPhotoBusy: Boolean,
    photoUri: Uri?
) {

    val photoBusy = remember {
        mutableStateOf(false)
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .height(maxWidth),
            backgroundColor = ultraLightGray,
            elevation = 0.dp
        ) {
            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = "profile_photo",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> photoBusy.value = true
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> photoBusy.value = true
                            else -> {
                                photoBusy.value = false
                                NonPhotoField()
                            }
                        }
                    }
                    else -> {
                        when (isPhotoBusy) {
                            true -> {
                                SubcomposeAsyncImageContent()
                                photoBusy.value = true
                            }
                            else -> {
                                photoBusy.value = false
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }
            }
            if (photoBusy.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        color = petShelterBlue
                    )
                }

            }
        }
    }
}

@Composable
fun NonPhotoField() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_image_field),
            contentDescription = stringResource(id = R.string.photo_field),
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
    }
}

@Preview
@Composable
fun DetailAnimalScreenPreview() {

    val uiState = remember {
        mutableStateOf(
            Announcement(
                "описание потеряшки описаниеописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшки потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшки",
                GeoPosition(LocateData().latPhoto!!, LocateData().lngPhoto!!),
                "2",
                null,
                "dog",
                "Заголовок животного"
            )
        )
    }
    PetShelterTheme {
        DetailAnimalScreen(
            uiState,
            rememberNavController(),
            { d, s -> },
            {},
            MutableStateFlow(1),
            {}
        )
    }
}