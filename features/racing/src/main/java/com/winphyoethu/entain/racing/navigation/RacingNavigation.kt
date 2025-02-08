package com.winphyoethu.entain.racing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.winphyoethu.entain.racing.RacingScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object RacingScreen

fun NavController.navigateToRacingScreen(navOptions: NavOptions?) {
    navigate(RacingScreen, navOptions)
}

fun NavGraphBuilder.createRacingScreen() {
    composable<RacingScreen> {
        RacingScreenRoute()
    }
}