package com.ajeeb.bumblecar.main.data.repository

import android.content.Context
import com.ajeeb.bumblecar.common.data.utils.autoComplete
import com.ajeeb.bumblecar.common.domain.utils.GenericResult
import com.ajeeb.bumblecar.common.domain.utils.Issues
import com.ajeeb.bumblecar.main.domain.repository.GooglePlacesRepository
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class GooglePlacesRepositoryImpl(
    private val placesClient: PlacesClient, private val context: Context
) : GooglePlacesRepository {

    override suspend fun getStreets(
        queryString: String, country: String?
    ): GenericResult<ArrayList<String>, Issues.Network> {
        val request = FindAutocompletePredictionsRequest.builder().apply {
            typesFilter = mutableListOf(PlaceTypes.ROUTE)
            sessionToken = AutocompleteSessionToken.newInstance()
            query = queryString
            country?.let { setCountries(it) }
        }.build()

        val result = placesClient.autoComplete(request)
        return GenericResult.Success(result)
    }
}