package com.ajeeb.bumblecar.main.domain.usecase

import com.ajeeb.bumblecar.common.domain.utils.GenericResult
import com.ajeeb.bumblecar.common.domain.utils.Issues
import com.ajeeb.bumblecar.main.domain.repository.GooglePlacesRepository
import javax.inject.Inject

/**
 * Use case for retrieving street suggestions based on a query string.
 *
 * This class encapsulates the logic for fetching a list of street name suggestions
 * from a [GooglePlacesRepository]. It handles the interaction with the repository
 * and returns the results as a [GenericResult] containing either a list of strings
 * (street suggestions) or a [Issues.Network] error.
 *
 * @property googlePlacesRepository The repository responsible for interacting with the Google Places API.
 */
class GetStreetSuggestionsUseCase @Inject constructor(
    private val googlePlacesRepository: GooglePlacesRepository
) {
    suspend operator fun invoke(queryString: String): GenericResult<ArrayList<String>, Issues.Network> {
        return googlePlacesRepository.getStreets(queryString = queryString, country = null)
    }
}