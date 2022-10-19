package com.example.petshelter.authScreens.main.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.common.AuthTabItem
import com.example.petshelter.authScreens.main.components.AuthTabs
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.authScreens.main.model.AuthUiState
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.ui.theme.PetShelterTheme
import com.example.petshelter.utils.UiText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthScreen(
    uiState: AuthUiState,
    navigateCallback: (AppScreens) -> Unit,
    joinEmailCallback: (String) -> Unit,
    joinPasswordCallback: (String) -> Unit,
    nameCallback: (String) -> Unit,
    registerEmailCallback: (String) -> Unit,
    registerPasswordCallback: (String) -> Unit,
    repeatPasswordCallback: (String) -> Unit,
    registerCallback: () -> Unit,
    joinCallback: () -> Unit
) {


    val context = LocalContext.current.applicationContext
    uiState.errorMessage.observe(
        LocalContext.current as LifecycleOwner
    ) {
        if (it.isNotEmpty()) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    val screenBusy = uiState.screenBusy.observeAsState(false)

    PetShelterTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = petShelterWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 30.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(0.050f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.ic_pet_logo2),
                        contentDescription = "logo2",
                    )
                    Image(
                        modifier = Modifier.fillMaxHeight(0.75f),
                        painter = painterResource(id = R.drawable.ic_pet_shelter2),
                        contentDescription = "logoText2",
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                AuthTabs(
                    uiState = uiState,
                    forgetPassCallback = navigateCallback,
                    joinEmailCallback = joinEmailCallback,
                    joinPasswordCallback = joinPasswordCallback,
                    nameCallback = nameCallback,
                    registerEmailCallback = registerEmailCallback,
                    registerPasswordCallback = registerPasswordCallback,
                    repeatPasswordCallback = repeatPasswordCallback,
                    registerCallback = registerCallback,
                    joinCallback = joinCallback
                )

                TextButton(modifier = Modifier.wrapContentSize(),
                    onClick = { navigateCallback.invoke(AppScreens.MainAppScreen) }) {
                    Row {
                        Text(stringResource(R.string.signWithoutBtn), style = joinTextStyle)
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                            contentDescription = stringResource(
                                R.string.signInWithout
                            )
                        )
                    }
                }
            }
        }
        if (screenBusy.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = petShelterBlue)
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    PetShelterTheme {
        AuthScreen(
            AuthUiState(
                MutableLiveData<String>(""),
                MutableLiveData<String>(""),
                MutableLiveData<String>(""),
                MutableLiveData<String>(""),
                MutableLiveData<String>(""),
                MutableLiveData<String>(""),
                MutableLiveData<Boolean>(false),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData<UiText>(UiText.EmptyString),
                MutableLiveData("")
            ),
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}