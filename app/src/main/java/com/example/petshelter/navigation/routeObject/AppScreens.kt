package com.example.petshelter.navigation.routeObject

import androidx.navigation.NavOptions

const val loginPasswordSignUpRoute = "loginPasswordSignUp"

const val mainScreenRoute = "mainScreen"

const val popRouteName = "popRoute"

const val loggedUserGraph = "loggedGraph"

const val splashScreen = "splashScreen"
const val authSignUpRoute = "authScreen"
const val forgetPasswordRoute = "forgetPasswordScreen"





sealed class AppScreens(
    var route: String,
    val options: NavOptions? = null,
    val inclusive: Boolean = false,
    val saveState: Boolean = false,
    val popTargetRoute: String = ""
) {


    object SplashScreen:AppScreens(
        splashScreen
    )
    
    object AuthSignUp: AppScreens(
        authSignUpRoute,
        NavOptions.Builder().setPopUpTo(0, false).build()
    )
    object ForgetPass :AppScreens(forgetPasswordRoute)
    object LoginPasswordSignUp: AppScreens(
        loginPasswordSignUpRoute,
    )

    object MainAppScreen: AppScreens(
        route = mainScreenRoute,
        options = NavOptions.Builder().setPopUpTo(0, false).build()
    )


    object PopBackToMain: AppScreens(
        route = popRouteName,
        popTargetRoute = mainScreenRoute
    )

    object PopBackStack: AppScreens(popRouteName)

}
