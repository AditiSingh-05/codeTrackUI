package com.example.dummyui

sealed class AppScreens(val route: String) {
    object AuthScreen : AppScreens("auth_screen")
    object OnboardingScreen : AppScreens("onboarding_screen")
    object MainScreen : AppScreens("main_screen")
}