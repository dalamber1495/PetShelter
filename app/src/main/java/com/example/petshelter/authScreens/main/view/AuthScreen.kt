package com.example.petshelter.authScreens.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterWhite
import com.example.petshelter.R
import com.example.petshelter.authScreens.common.AuthTabItem
import com.example.petshelter.authScreens.main.components.AuthTabs
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.ui.theme.PetShelterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthScreen(
    navigateCallback:(AppScreens)->Unit,
) {

    val tabs = listOf(
        AuthTabItem.SignIn,
        AuthTabItem.Register,
    )
    val pagerState = rememberPagerState()

    PetShelterTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = petShelterWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 50.dp),
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
                AuthTabs(tabs = tabs, pagerState = pagerState,    navigateCallback)

                TextButton(modifier = Modifier.wrapContentSize(),
                    onClick = {navigateCallback.invoke(AppScreens.MainAppScreen)}) {
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
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    PetShelterTheme {
        AuthScreen(
            {}
        )
    }
}