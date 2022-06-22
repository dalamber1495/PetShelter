package com.example.petshelter.authScreens.main.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.FormField
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.authScreens.main.consts.*
import com.example.petshelter.ui.theme.PetShelterTheme


@Composable
fun ForgetPasswordScreen(
    backArrowCallback:()->Unit
) {

    PetShelterTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = petShelterWhite
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                TopAppBar(
                    modifier = Modifier.fillMaxHeight(0.1f),
                    title = {
                        Text(modifier  = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp),
                            textAlign = TextAlign.Start,
                            style = topBarTextStyle,
                            text = stringResource(R.string.reset_pass_topBar)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = backArrowCallback) {
                            Icon(modifier = Modifier.size(50.dp),
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Menu Btn"
                            )
                        }
                    },
                    backgroundColor = petShelterBlue,
                    contentColor = petShelterWhite,
                )
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 64.dp),
                    text = stringResource(R.string.forget_pass_text),
                style = forgetPassTextStyle,
                textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(40.dp))
                FormField(modifier = Modifier.height(56.dp),
                    placeHolder = "Email",
                    valueCallback = {})
                Spacer(modifier = Modifier.height(40.dp))
                PetShelterBtn(modifier = Modifier
                    .wrapContentWidth()
                    .height(60.dp),
                    "Продолжить",
                    clickCallback = {})
            }

        }
    }

}

@Preview
@Composable
fun ForgetPasswordScreenPreview() {
    PetShelterTheme() {
        ForgetPasswordScreen(
            {}
        )
    }
}