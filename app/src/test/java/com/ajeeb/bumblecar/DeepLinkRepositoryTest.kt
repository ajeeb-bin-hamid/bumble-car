package com.ajeeb.bumblecar

import com.ajeeb.bumblecar.main.data.repository.DeepLinkRepositoryImpl
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * [DeepLinkRepositoryTest] is a test class for [DeepLinkRepositoryImpl].
 *
 * This class contains unit tests to verify the behavior of the [DeepLinkRepositoryImpl]'s
 * `generateDeepLink` function, ensuring it correctly constructs deep links based on provided parameters.
 *
 * It utilizes JUnit4 for test execution and assertions.
 */
@RunWith(JUnit4::class)
class DeepLinkRepositoryTest {

    private val deepLinkRepository = DeepLinkRepositoryImpl()

    @Test
    fun `test generateDeepLink with valid inputs`() {
        // Given
        val pickUpPoint = "New York"
        val dropOffPoint = "Los Angeles"
        val pickUpDate = "2024-03-10"
        val dropOffDate = "2024-03-20"

        // Expected Output (URL Encoded)
        val expectedUrl =
            "https://www.kayak.com/in?a=awesomecars&url=/cars/New+York/Los+Angeles/2024-03-10/2024-03-20"

        // When
        val actualUrl =
            deepLinkRepository.generateDeepLink(pickUpPoint, dropOffPoint, pickUpDate, dropOffDate)

        // Then
        assertEquals(expectedUrl, actualUrl)
    }
}