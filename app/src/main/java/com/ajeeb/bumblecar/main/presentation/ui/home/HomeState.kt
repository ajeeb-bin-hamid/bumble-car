package com.ajeeb.bumblecar.main.presentation.ui.home

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class HomeState(
    val pickUpPoint: String = "",
    val dropOffPoint: String = "",
    val pickUpSuggestions: ArrayList<String> = arrayListOf(),
    val dropOffSuggestions: ArrayList<String> = arrayListOf(),
    val pickUpDate: String? = null,
    val dropOffDate: String? = null,
    val isErrorOnPickUpPoint: Boolean = false,
    val isErrorOnDropOffPoint: Boolean = false,
    val isErrorOnPickUpDate: Boolean = false,
    val isErrorOnDropOffDate: Boolean = false,
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}