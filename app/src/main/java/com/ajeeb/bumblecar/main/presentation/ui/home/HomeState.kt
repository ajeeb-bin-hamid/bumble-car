package com.ajeeb.bumblecar.main.presentation.ui.home

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class HomeState(
    val pickUpPoint: String = "",
    val dropOffPoint: String = "",
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}