package com.ajeeb.bumblecar.common.data.utils

import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**This function, autoComplete, is an extension function for PlacesClient that performs an
 * asynchronous autocomplete request using the FindAutocompletePredictionsRequest.
 * It processes the Google Places API autocomplete predictions and returns
 * the results in a custom data class, GooglePlacesResult.*/
suspend fun PlacesClient.autoComplete(request: FindAutocompletePredictionsRequest): ArrayList<String> {
    val namesList: ArrayList<String> = ArrayList()

    return suspendCoroutine { continuation ->
        findAutocompletePredictions(request).addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions.toList()
            for (prediction in predictions) {
                namesList.add(prediction.getPrimaryText(null).toString())
            }
            continuation.resume(namesList)

        }.addOnFailureListener {
            continuation.resume(arrayListOf())
        }
    }
}