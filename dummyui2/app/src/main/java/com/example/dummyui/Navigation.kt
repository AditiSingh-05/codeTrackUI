package com.example.dummyui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummyui.ui.presentation.AuthScreen
import com.example.dummyui.ui.presentation.OnboardingScreen

import com.example.dummyui.ui.presentation.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.AuthScreen.route) {
        composable(AppScreens.AuthScreen.route) { AuthScreen(navController = navController) }
        composable(AppScreens.OnboardingScreen.route) { OnboardingScreen(navController = navController) }
        composable(AppScreens.MainScreen.route) { MainScreen(navController = navController) }
    }
}