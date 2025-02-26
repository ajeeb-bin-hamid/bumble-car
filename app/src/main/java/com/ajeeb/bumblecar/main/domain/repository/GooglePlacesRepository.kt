package com.ajeeb.bumblecar.main.domain.repository


import com.ajeeb.bumblecar.common.domain.utils.GenericResult
import com.ajeeb.bumblecar.common.domain.utils.Issues

interface GooglePlacesRepository {
    suspend fun getStreets(
        queryString: String, country: String?
    ): GenericResult<ArrayList<String>, Issues.Network>
}