package com.ajeeb.bumblecar.main.domain.repository

interface DeepLinkRepository {

    fun generateDeepLink(
        pickUpPoint: String, dropOffPoint: String, pickUpDate: String, dropOffDate: String
    ): String
}