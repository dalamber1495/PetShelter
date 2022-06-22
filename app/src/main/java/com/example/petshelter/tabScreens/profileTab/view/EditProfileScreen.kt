package com.example.petshelter.tabScreens.profileTab.view

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.eql.consts.ui.colors.petShelterBlack
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.FormField
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.tabScreens.mainScreen.consts.ultraLightGray
import com.example.petshelter.tabScreens.profileTab.model.ProfileTabUiState
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import com.example.petshelter.ui.styles.profileTextStyle
import java.io.File
import java.util.*

@Composable
fun EditProfileScreen(
    addPhotoCallback: (Uri?) -> Unit,
    uiState: ProfileTabUiState,
    popBackStackCallback:(NavController)->Unit,
    navController: NavController,
){
    val screenIsBusy = uiState.screenBusy.observeAsState(false)
    val avatarUri = uiState.avatarUri.observeAsState(null)
    val nameAndSurname = uiState.nameAndSurname.observeAsState("Имя и фамилия")

    val context = LocalContext.current
    val cropImage = rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
        when (result.isSuccessful) {
            true -> {
                val uriContent = result.uriContent
                if (uriContent != null) addPhotoCallback.invoke(
                    uriContent
                )
            }
            else -> {
                val exception = result.error
                Log.e("AVATAR_TAG", "error: $exception")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopBarCreateAnnouncement(
            backArrowShow = true,
            backArrowCallback = {popBackStackCallback.invoke(navController)},
            textTopBar = "Изменение профиля"
        )
        Spacer(modifier = Modifier.height(48.dp))
        ChangeProfilePhoto(
            isPhotoBusy = screenIsBusy.value,
            photoUri = avatarUri.value,
            context = context,
            cropImage = cropImage
        )
        Spacer(modifier = Modifier.height(40.dp))

        FormField(modifier = Modifier.height(56.dp), placeHolder = nameAndSurname.value) {

        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PetShelterBtn(modifier = Modifier
                .width(165.dp)
                .height(56.dp), text = "Продолжить") {
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
fun ChangeProfilePhoto(
    isPhotoBusy: Boolean,
    photoUri: Uri?,
    context: Context,
    cropImage: ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>
) {
    Surface(
        modifier = Modifier
            .size(184.dp)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                takeChangePhotoProfile(context = context, cropImage = cropImage)
            }) {

        Card(
            modifier = Modifier.wrapContentSize(),
            shape = CircleShape,
            backgroundColor = ultraLightGray,
            elevation = 0.dp
        ) {
            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = "profile_photo",
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> CircularProgressIndicator(color = petShelterBlue)
                            else -> {
                                NonChangePhotoField()
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
        Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Image(painter = painterResource(id = R.drawable.ic_text_button_fluid), contentDescription = "change photo icon")
        }
    }
}


fun takeChangePhotoProfile(
    context: Context,
    cropImage: ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>
) {

    val path = context.filesDir
    val letDirectory = File(path, "images")
    letDirectory.mkdirs()
    if (!letDirectory.exists()) {
        letDirectory.mkdir()
    }

    val file = Uri.fromFile(File(letDirectory, "avatar-${UUID.randomUUID()}.png"))
    cropImage.launch(
        options {
            setOutputUri(file)
            setBackgroundColor(Color.Transparent.hashCode())
            setBorderCornerColor(Color.Transparent.hashCode())
            setGuidelines(CropImageView.Guidelines.ON_TOUCH)
            setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            setAspectRatio(1, 1)
            setCropShape(CropImageView.CropShape.OVAL)
            setMinCropResultSize(150, 150)
        }
    )
}

@Composable
fun NonChangePhotoField() {
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
