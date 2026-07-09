package com.ishika.eventsexplorer.util

import org.junit.Assert.*
import org.junit.Test

class DistanceUtilTest {

    @Test
    fun sameLocation_returnsZeroDistance() {

        val distance = DistanceUtil.calculateDistanceKm(
            43.5448,
            -80.2482,
            43.5448,
            -80.2482
        )

        assertEquals(0.0, distance, 0.1)
    }

    @Test
    fun differentLocations_returnsPositiveDistance() {

        val distance = DistanceUtil.calculateDistanceKm(
            43.5448,
            -80.2482,
            43.6532,
            -79.3832
        )

        assertTrue(distance > 0)
    }

    @Test
    fun formatDistance_returnsCorrectString() {

        val text = DistanceUtil.formatDistance(12.5)

        assertEquals("12.5 km away", text)
    }
}