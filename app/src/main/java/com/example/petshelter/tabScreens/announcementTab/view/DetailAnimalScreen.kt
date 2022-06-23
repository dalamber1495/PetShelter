package com.example.petshelter.tabScreens.announcementTab.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.MutableLiveData
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberImagePainter
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.tabScreens.announcementTab.model.DetailAnimalUiState
import com.example.petshelter.tabScreens.announcementTab.model.LocateData
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.tabScreens.mainScreen.consts.ultraLightGray
import com.example.petshelter.ui.styles.dialogTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailAnimalScreen(
    uiState: DetailAnimalUiState,
    backButtonCallback: () -> Unit,
    initCallback: () -> Unit
) {
    val title = uiState.title.observeAsState("Заголовок объявления")
    val description = uiState.description.observeAsState("")
    val address = uiState.address.observeAsState("")
    val photo = uiState.photo.observeAsState(listOf())
    val locatePhoto = uiState.locatePhoto.observeAsState(LocateData())

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarCreateAnnouncement(
                backArrowShow = true,
                textTopBar = title.value,
                backArrowCallback = backButtonCallback
            )
        }
    ) {
        Column(
            modifier = Modifier.verticalScroll(
                state = scrollState,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AnimalPhoto(isPhotoBusy = false, photoUri = photo.value.first())
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
                Text(modifier = Modifier.fillMaxWidth(0.4f)
                    .padding(horizontal = 2.dp),text = locatePhoto.value.latPhoto.toString() + " " + locatePhoto.value.lngPhoto.toString())
                PetShelterBtn(
                    modifier = Modifier
                        .width(136.dp)
                        .height(48.dp),
                    text = "На карте"
                ) {

                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(text = description.value, style = dialogTextStyle)
            }
        }
    }

}

@Composable
fun AnimalPhoto(
    isPhotoBusy: Boolean,
    photoUri: Uri?
) {
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
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> CircularProgressIndicator(color = petShelterBlue)
                            else -> {
                                NonPhotoField()
                            }
                        }
                    }
                    else -> {
                        when (isPhotoBusy) {
                            true -> {
                                SubcomposeAsyncImageContent()
                                CircularProgressIndicator(
                                    color = petShelterBlue,
                                    strokeWidth = 4.dp
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
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

    val uiState = DetailAnimalUiState(
        MutableLiveData("Заголовок животного"),
        MutableLiveData("описание потеряшки описаниеописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшки потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшкиописание потеряшки"),
        MutableLiveData(listOf(null)),
        MutableLiveData(""),
        MutableLiveData(LocateData())
    )
    PetShelterTheme {
        DetailAnimalScreen(
            uiState,
            {},
            {}
        )
    }
}