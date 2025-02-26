package com.ajeeb.bumblecar.main.presentation.ui.home

sealed class HomeIntent {
    data class SetPickUpPoint(val text: String) : HomeIntent()
    data class SetDropOffPoint(val text: String) : HomeIntent()
    data class SearchPickUpPoint(val text: String) : HomeIntent()
    data class SearchDropOffPoint(val text: String) : HomeIntent()
}