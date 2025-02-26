package com.ajeeb.bumblecar.main.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.bumblecar.common.domain.utils.GenericResult
import com.ajeeb.bumblecar.common.presentation.utils.currentState
import com.ajeeb.bumblecar.common.presentation.utils.postSideEffect
import com.ajeeb.bumblecar.common.presentation.utils.reduceState
import com.ajeeb.bumblecar.main.domain.usecase.GetStreetSuggestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStreetSuggestionsUseCase: GetStreetSuggestionsUseCase
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    private val initialState = savedStateHandle.toRoute<HomeState>()
    override val container = viewModelScope.container<HomeState, HomeSideEffect>(initialState)

    fun onEvent(event: HomeIntent) {
        when (event) {
            is HomeIntent.SetPickUpPoint -> setPickUpPoint(event.text)
            is HomeIntent.SetDropOffPoint -> setDropOffPoint(event.text)
            is HomeIntent.SearchPickUpPoint -> searchPickUpPoint(event.text)
            is HomeIntent.SearchDropOffPoint -> searchDropOffPoint(event.text)
            is HomeIntent.SetPickUpDate -> setPickUpDate(event.date)
            is HomeIntent.SetDropOffDate -> setDropOffDate(event.date)
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

    private fun searchPickUpPoint(text: String) {
        viewModelScope.launch {
            val currentText = currentState.pickUpPoint
            if (currentText != text) {
                reduceState { copy(pickUpPoint = text) }

                when (val pickUpPointsCall = getStreetSuggestionsUseCase(text)) {
                    is GenericResult.Success -> {
                        reduceState { copy(pickUpSuggestions = pickUpPointsCall.data) }
                    }

                    is GenericResult.Error -> pickUpPointsCall.error.message?.let {
                        postSideEffect { HomeSideEffect.ShowToast(it) }
                    }
                }
            }
        }
    }

    private fun searchDropOffPoint(text: String) {
        viewModelScope.launch {
            val currentText = currentState.dropOffPoint
            if (currentText != text) {
                reduceState { copy(dropOffPoint = text) }

                when (val dropOffPointsCall = getStreetSuggestionsUseCase(text)) {
                    is GenericResult.Success -> {
                        reduceState { copy(dropOffSuggestions = dropOffPointsCall.data) }
                    }

                    is GenericResult.Error -> dropOffPointsCall.error.message?.let {
                        postSideEffect { HomeSideEffect.ShowToast(it) }
                    }
                }
            }

        }
    }

    private fun setPickUpDate(date: String) {
        viewModelScope.launch {
            reduceState { copy(pickUpDate = date) }
        }
    }


    private fun setDropOffDate(date: String) {
        viewModelScope.launch {
            reduceState { copy(dropOffDate = date) }
        }
    }
}