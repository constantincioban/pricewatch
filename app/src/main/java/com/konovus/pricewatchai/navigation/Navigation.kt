package com.example.naturewhispers.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konovus.pricewatchai.presentation.ui.addItemScreen.AddItemScreen
import com.konovus.pricewatchai.presentation.ui.mainScreen.MainScreen

@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    val actions = remember(navController) { Actions(navController) }
    val startDestination = Screens.Main.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(
            route = Screens.Main.route,
        ) {
            MainScreen(
                navigateTo = { route, params -> actions.navigateTo(route, params) },
            )
        }

        composable(
            route = Screens.AddItem.routeWithArgs,
            arguments = Screens.AddItem.arguments
        ) { backStackEntry ->
            AddItemScreen()
        }


    }

}