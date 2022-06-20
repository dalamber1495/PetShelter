package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.tabScreens.createAnnouncementTab.model.FirstStepAddPhotoData
import com.example.petshelter.ui.theme.PetShelterTheme
import java.io.File
import java.util.*

@Composable
fun FirstStepCreateAnnouncementForm(
    screenIsBusy: Boolean,
    avatarUri: Uri?,
    addPhotoCallback: (FirstStepAddPhotoData) -> Unit,
    firstStepReadyCallback: () -> Unit
) {

    val context = LocalContext.current
    val cropImage = rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
        when (result.isSuccessful) {
            true -> {
                val uriContent = result.uriContent
                if (uriContent != null) addPhotoCallback.invoke(
                    FirstStepAddPhotoData(
                        uriContent
                    )
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
        when (avatarUri != null) {
            true -> {
                TopBarCreateAnnouncement(
                    true,
                    stringResource(R.string.upload_photo),
                    {})
                AnimalPhoto(
                    isPhotoBusy = screenIsBusy,
                    photoUri = avatarUri,
                    firstStepReadyCallback = firstStepReadyCallback,
                    context = context,
                    cropImage = cropImage
                )
            }
            false -> {
                TopBarCreateAnnouncement(
                    false,
                    stringResource(R.string.upload_photo),
                    {})
                AddPhotoBtn(context = context, cropImage = cropImage)
            }
        }


    }
}

@Composable
fun AddPhotoBtn(
    context: Context,
    cropImage: ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PetShelterBtn(
            modifier = Modifier
                .width(218.dp)
                .height(56.dp),
            text = "Добавить фото",
            image = R.drawable.ic_file_download,
            clickCallback = { takePhoto(context = context, cropImage = cropImage) }
        )
    }
}

@Composable
fun AnimalPhoto(
    photoUri: Uri?,
    isPhotoBusy: Boolean,
    firstStepReadyCallback: () -> Unit?,
    context: Context,
    cropImage: ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                takePhoto(context = context, cropImage = cropImage)
            }) {

        Card(
            modifier = Modifier.wrapContentSize(),
            elevation = 0.dp
        ) {
            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = "animal_photo",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> CircularProgressIndicator(color = petShelterBlue)
                            else -> {
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PetShelterBtn(
            modifier = Modifier
                .width(205.dp)
                .height(56.dp),
            text = "Указать адрес",
            imageAfter = R.drawable.ic_baseline_arrow_forward_ios_24_btn,
            clickCallback = {firstStepReadyCallback.invoke()}
        )
        Spacer(modifier = Modifier.height(24.dp))

    }
}

fun takePhoto(
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
            setMinCropResultSize(150, 150)
        }
    )

}

@Preview
@Composable
fun FirstStepCreateAnnouncementFormPreview() {
    PetShelterTheme {
        FirstStepCreateAnnouncementForm(
            screenIsBusy = false,
            avatarUri = null,
            firstStepReadyCallback = {},
            addPhotoCallback = {}
        )
    }
}