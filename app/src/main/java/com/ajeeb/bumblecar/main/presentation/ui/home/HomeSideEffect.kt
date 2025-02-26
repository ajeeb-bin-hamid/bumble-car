package com.ajeeb.bumblecar.main.presentation.ui.home


sealed class HomeSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : HomeSideEffect()

    // Actions that can be used to invoke functions on the ViewModel
}