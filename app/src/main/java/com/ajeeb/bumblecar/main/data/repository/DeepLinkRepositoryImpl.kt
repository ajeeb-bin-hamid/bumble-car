package com.ajeeb.bumblecar.main.data.repository

import com.ajeeb.bumblecar.main.data.utils.AFFILIATE_ID
import com.ajeeb.bumblecar.main.data.utils.KAYAK_DOMAIN
import com.ajeeb.bumblecar.main.domain.repository.DeepLinkRepository
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class DeepLinkRepositoryImpl : DeepLinkRepository {

    /**
     * Generates a deep link to the Kayak website for car rental search based on the provided parameters.
     *
     * This function constructs a URL that, when opened, will take the user directly to the Kayak car rental search
     * results page with the specified pickup point, drop-off point, pickup date, and drop-off date pre-filled.
     *
     * @param pickUpPoint The city or location where the user wants to pick up the rental car (e.g., "London").
     *                    This will be URL-encoded to handle spaces and special characters.
     * @param dropOffPoint The city or location where the user wants to drop off the rental car (e.g., "Paris").
     *                     This will also be URL-encoded.
     * @param pickUpDate The desired pickup date in a specific format (e.g., "2024-03-15").
     * @param dropOffDate The desired drop-off date in a specific format (e.g., "2024-03-20").
     * @return A String representing the complete Kayak deep link URL.
     *
     * @throws java.io.UnsupportedEncodingException If UTF-8 encoding is not supported. This is highly unlikely in modern JVMs.
     *
     * Example:
     * ```
     * val deepLink = generateDeepLink("New York", "Los Angeles", "2024-12-25", "2025-01-01")
     * println(deepLink)
     * // Expected output (affiliate id and domain will be based on config, this is an example):
     * // https://www.kayak.com/in?a=myAffiliateID&url=/cars/New%20York/Los%20Angeles/2024-12-25/2025-01-01
     * ```
     */
    override fun generateDeepLink(
        pickUpPoint: String, dropOffPoint: String, pickUpDate: String, dropOffDate: String
    ): String {
        val encodedPickUpPoint = URLEncoder.encode(pickUpPoint, StandardCharsets.UTF_8.toString())
        val encodedDropOffPoint = URLEncoder.encode(dropOffPoint, StandardCharsets.UTF_8.toString())
        val url =
            "https://$KAYAK_DOMAIN/in?a=$AFFILIATE_ID&url=/cars/$encodedPickUpPoint/$encodedDropOffPoint/$pickUpDate/$dropOffDate"
        return url
    }
}