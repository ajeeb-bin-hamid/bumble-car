package com.ajeeb.bumblecar.main.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.bumblecar.common.presentation.utils.reduceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    private val initialState = savedStateHandle.toRoute<HomeState>()
    override val container = viewModelScope.container<HomeState, HomeSideEffect>(initialState)

    fun onEvent(event: HomeIntent) {
        when (event) {
            is HomeIntent.SetPickUpPoint -> setPickUpPoint(event.text)
            is HomeIntent.SetDropOffPoint -> setDropOffPoint(event.text)
        }
    }

    private fun setPickUpPoint(text: String) {
        viewModelScope.launch {
            reduceState { copy(pickUpPoint = text) }
        }
    }

    private fun setDropOffPoint(text: String) {
        viewModelScope.launch {
            reduceState { copy(dropOffPoint = text) }
        }
    }
}