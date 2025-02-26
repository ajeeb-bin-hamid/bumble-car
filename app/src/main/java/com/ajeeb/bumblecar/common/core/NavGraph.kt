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

@Composable
fun navGraph() {
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