package com.ajeeb.bumblecar.common.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajeeb.bumblecar.main.presentation.ui.home.HomeScreen
import com.ajeeb.bumblecar.main.presentation.ui.home.HomeState
import com.ajeeb.bumblecar.main.presentation.ui.home.HomeViewModel

/**
 * [NavGraph] is the main navigation graph for the application.
 *
 * It utilizes Jetpack Compose Navigation to manage the different screens in the app.
 * This function defines the navigation structure and sets up the destinations
 * and their respective composables.
 *
 * Currently, it defines a single destination: the [HomeScreen].
 *
 * The navigation is managed using a [NavHost], and the navigation controller is
 * handled by [rememberNavController].
 *
 * The start destination is set to [HomeState], which represents the initial screen
 * of the application.
 *
 * For each composable destination:
 * - A [HomeViewModel] instance is obtained using [hiltViewModel].
 * - The view model's state is collected as a composable state using [collectAsState].
 * - The view model's side effects flow is exposed.
 * - The view model's event handler is exposed.
 * - The corresponding screen composable ([HomeScreen]) is invoked with the state, side effects and event handler.
 *
 * @Composable
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeState()) {
        composable<HomeState> {

            val vm = hiltViewModel<HomeViewModel>()
            val state = vm.container.stateFlow.collectAsState()
            val sideEffect = vm.container.sideEffectFlow
            val onEvent = vm::onEvent

            HomeScreen(state, sideEffect, onEvent)
        }
    }
}