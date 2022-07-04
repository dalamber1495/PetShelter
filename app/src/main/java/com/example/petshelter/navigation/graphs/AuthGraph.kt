package com.example.petshelter.navigation.graphs

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.petshelter.authScreens.main.view.AuthScreen
import com.example.petshelter.authScreens.main.view.ForgetPasswordScreen
import com.example.petshelter.authScreens.main.viewModel.AuthViewModel
import com.example.petshelter.authScreens.main.viewModel.ForgetPasswordViewModel
import com.example.petshelter.navigation.routeObject.AppScreens
import com.example.petshelter.ui.notLoggedUserGraph

@ExperimentalComposeUiApi
fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AppScreens.AuthSignUp.route, route = notLoggedUserGraph) {
        composable(route = AppScreens.AuthSignUp.route) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            AuthScreen(
                uiState = authViewModel.uiState,
                navigateCallback = authViewModel::navigateTo,
                joinEmailCallback = authViewModel::joinEmailCallback,
                joinPasswordCallback = authViewModel::joinPasswordCallback,
                nameCallback = authViewModel::nameCallback,
                registerEmailCallback = authViewModel::registerEmailCallback,
                registerPasswordCallback = authViewModel::registerPasswordCallback,
                repeatPasswordCallback = authViewModel::repeatPasswordCallback,
                registerCallback = authViewModel::registerCallback,
                joinCallback = authViewModel::joinCallback
            )
        }
        composable(route = AppScreens.ForgetPass.route) {
            val forgetPasswordViewModel = hiltViewModel<ForgetPasswordViewModel>()
            ForgetPasswordScreen(forgetPasswordViewModel::backToAuthScreen)
        }
    }
}