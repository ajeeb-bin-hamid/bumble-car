package com.ajeeb.bumblecar.main.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.bumblecar.common.domain.utils.GenericResult
import com.ajeeb.bumblecar.common.presentation.utils.currentState
import com.ajeeb.bumblecar.common.presentation.utils.postSideEffect
import com.ajeeb.bumblecar.common.presentation.utils.reduceState
import com.ajeeb.bumblecar.main.domain.usecase.GenerateDeepLinkUseCase
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
    private val getStreetSuggestionsUseCase: GetStreetSuggestionsUseCase,
    private val generateDeepLinkUseCase: GenerateDeepLinkUseCase
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
            is HomeIntent.GenerateDeepLink -> generateDeepLink()
        }
    }

    private fun setPickUpPoint(text: String) {
        viewModelScope.launch {
            reduceState { copy(pickUpPoint = text, isErrorOnPickUpPoint = false) }
        }
    }

    private fun setDropOffPoint(text: String) {
        viewModelScope.launch {
            reduceState { copy(dropOffPoint = text, isErrorOnDropOffPoint = false) }
        }
    }

    private fun searchPickUpPoint(text: String) {
        viewModelScope.launch {
            val currentText = currentState.pickUpPoint
            if (currentText != text) {
                reduceState { copy(pickUpPoint = text, isErrorOnPickUpPoint = false) }

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
                reduceState { copy(dropOffPoint = text, isErrorOnDropOffPoint = false) }

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
            reduceState { copy(pickUpDate = date, isErrorOnPickUpDate = false) }
        }
    }


    private fun setDropOffDate(date: String) {
        viewModelScope.launch {
            reduceState { copy(dropOffDate = date, isErrorOnDropOffDate = false) }
        }
    }

    private fun generateDeepLink() {
        viewModelScope.launch {
            val pickUpPoint = currentState.pickUpPoint
            val dropOffPoint = currentState.dropOffPoint
            val pickUpDate = currentState.pickUpDate
            val dropOffDate = currentState.dropOffDate

            if (pickUpPoint.isNotBlank() && dropOffPoint.isNotBlank() && !pickUpDate.isNullOrBlank() && !dropOffDate.isNullOrBlank()) {
                val deepLink = generateDeepLinkUseCase(
                    pickUpPoint = pickUpPoint,
                    dropOffPoint = dropOffPoint,
                    pickUpDate = pickUpDate,
                    dropOffDate = dropOffDate
                )

                postSideEffect {
                    HomeSideEffect.OpenDeepLink(deepLink)
                }
            } else when {
                pickUpPoint.isBlank() -> reduceState {
                    copy(isErrorOnPickUpPoint = true)
                }

                dropOffPoint.isBlank() -> reduceState {
                    copy(isErrorOnDropOffPoint = true)
                }

                pickUpDate.isNullOrBlank() -> reduceState {
                    copy(isErrorOnPickUpDate = true)
                }

                dropOffDate.isNullOrBlank() -> reduceState {
                    copy(isErrorOnDropOffDate = true)
                }
            }
        }
    }
}