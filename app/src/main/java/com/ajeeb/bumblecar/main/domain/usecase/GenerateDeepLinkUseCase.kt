package com.ajeeb.bumblecar.main.domain.usecase

import com.ajeeb.bumblecar.main.domain.repository.DeepLinkRepository
import javax.inject.Inject

class GenerateDeepLinkUseCase @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository
) {

    /**
     * Generates a deep link for booking a service based on the provided parameters.
     *
     * This function takes the pickup point, drop-off point, pickup date, and drop-off date
     * as input and delegates the deep link generation to the `deepLinkRepository`.
     *
     * @param pickUpPoint The location where the service will start (e.g., "Airport", "City Center").
     * @param dropOffPoint The location where the service will end (e.g., "Hotel", "Home Address").
     * @param pickUpDate The date when the service will start (e.g., "2023-12-25").
     * @param dropOffDate The date when the service will end (e.g., "2023-12-30").
     * @return A string representing the generated deep link.
     */
    operator fun invoke(
        pickUpPoint: String, dropOffPoint: String, pickUpDate: String, dropOffDate: String
    ): String {

        return deepLinkRepository.generateDeepLink(
            pickUpPoint = pickUpPoint,
            dropOffPoint = dropOffPoint,
            pickUpDate = pickUpDate,
            dropOffDate = dropOffDate
        )
    }
}