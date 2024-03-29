package com.example.petshelter.authScreens.main.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlack
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.common.AuthTabItem
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.authScreens.main.consts.tabTextStyle
import com.example.petshelter.authScreens.main.model.AuthUiState
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.utils.UiText
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthTabs(
    uiState: AuthUiState,
    forgetPassCallback: (AppScreens) -> Unit,
    joinEmailCallback: (String) -> Unit,
    joinPasswordCallback: (String) -> Unit,
    nameCallback: (String) -> Unit,
    registerEmailCallback: (String) -> Unit,
    registerPasswordCallback: (String) -> Unit,
    repeatPasswordCallback: (String) -> Unit,
    registerCallback: () -> Unit,
    joinCallback: () -> Unit
) {
    val tabs = listOf(
        AuthTabItem.SignIn,
        AuthTabItem.Register,
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(5.dp)
            )
        },
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = petShelterBlue,
        divider = {
            TabRowDefaults.Divider(color = petShelterBlue, modifier = Modifier.width(5.dp))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = pagerState.currentPage == index
            Tab(
                modifier = Modifier,
                text = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = tab.route,
                            color = petShelterBlack,
                            style = tabTextStyle
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                },
                selected = selected,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
    AuthTabsContent(
        tabs = tabs,
        pagerState = pagerState,
        uiState = uiState,
        forgetPassCallback = forgetPassCallback,
        joinEmailCallback = joinEmailCallback,
        joinPasswordCallback = joinPasswordCallback,
        nameCallback = nameCallback,
        registerEmailCallback = registerEmailCallback,
        registerPasswordCallback = registerPasswordCallback,
        repeatPasswordCallback = repeatPasswordCallback,
        registerCallback = registerCallback,
        joinCallback = joinCallback
    )
}

@OptIn(ExperimentalPagerApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun AuthTabsContent(
    tabs: List<AuthTabItem>,
    pagerState: PagerState,
    uiState: AuthUiState,
    forgetPassCallback: (AppScreens) -> Unit,
    joinEmailCallback: (String) -> Unit,
    joinPasswordCallback: (String) -> Unit,
    nameCallback: (String) -> Unit,
    registerEmailCallback: (String) -> Unit,
    registerPasswordCallback: (String) -> Unit,
    repeatPasswordCallback: (String) -> Unit,
    registerCallback: () -> Unit,
    joinCallback: () -> Unit
) {
    val email = uiState.email.observeAsState("")
    val password = uiState.password.observeAsState("")
    val registerEmail = uiState.registerEmail.observeAsState("")
    val registerPassword = uiState.registerPassword.observeAsState("")
    val name = uiState.name.observeAsState("")
    val repeatPassword = uiState.repeatPassword.observeAsState("")
    val screenBusy = uiState.screenBusy.observeAsState(false)
    val validationJoinEmail = uiState.validationJoinEmail.observeAsState(UiText.EmptyString)
    val validationJoinPassword = uiState.validationJoinPassword.observeAsState(UiText.EmptyString)
    val validationRegisterEmail = uiState.validationRegisterEmail.observeAsState(UiText.EmptyString)
    val validationRegisterPassword =
        uiState.validationRegisterPassword.observeAsState(UiText.EmptyString)
    val validationName = uiState.validationName.observeAsState(UiText.EmptyString)
    val validationRepeatPassword =
        uiState.validationRepeatPassword.observeAsState(UiText.EmptyString)

    HorizontalPager(
        state = pagerState,
        count = tabs.size,
        modifier = Modifier.fillMaxHeight(0.9f)
    ) { page ->
        when (tabs[page]) {
            is AuthTabItem.SignIn -> {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column {
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                text = email.value,
                                placeHolder = "Email",
                                validationValue = validationJoinEmail.value,
                                valueCallback = joinEmailCallback
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                placeHolder = "Пароль",
                                visibleIcon = true,
                                validationValue = validationJoinPassword.value,
                                text = password.value,
                                valueCallback = joinPasswordCallback
                            )
                        }

                        PetShelterBtn(
                            modifier = Modifier
                                .width(147.dp)
                                .height(60.dp),
                            image = R.drawable.ic_lpet_btn,
                            text = "Войти",
                            clickCallback = joinCallback
                        )
                        TextButton(modifier = Modifier.wrapContentSize(),
                            onClick = { forgetPassCallback.invoke(AppScreens.ForgetPass) }) {
                            Row {
                                Text("Забыли пароль?", style = joinTextStyle)
                            }
                        }
                    }
                }
            }
            is AuthTabItem.Register -> {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column (verticalArrangement = Arrangement.SpaceAround){
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                text = name.value,
                                placeHolder = "Имя",
                                validationValue = validationName.value,
                                valueCallback = nameCallback
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                text = registerEmail.value,
                                placeHolder = "Email",
                                validationValue = validationRegisterEmail.value,
                                valueCallback = registerEmailCallback
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                text = registerPassword.value,
                                placeHolder = "Пароль",
                                visibleIcon = true,
                                validationValue = validationRegisterPassword.value,
                                valueCallback = registerPasswordCallback
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            FormField(
                                modifier = Modifier
                                    .height(56.dp),
                                text = repeatPassword.value,
                                placeHolder = "Повторите пароль",
                                visibleIcon = true,
                                validationValue = validationRepeatPassword.value,
                                valueCallback
                                = repeatPasswordCallback
                            )
                        }
                        PetShelterBtn(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(60.dp),
                            text = "Зарегистрироваться",
                            image = R.drawable.ic_lpet_btn,
                            clickCallback = registerCallback
                        )
                    }
                }
            }
        }
    }
}