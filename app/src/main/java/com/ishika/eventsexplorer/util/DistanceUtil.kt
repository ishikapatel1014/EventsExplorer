package com.ishika.eventsexplorer.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceUtil {
    fun calculateDistanceKm(
        userLat: Double,
        userLng: Double,
        eventLat: Double,
        eventLng: Double
    ): Double {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(eventLat - userLat)
        val dLng = Math.toRadians(eventLng - userLng)

        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(userLat)) * cos(Math.toRadians(eventLat)) *
            sin(dLng / 2) * sin(dLng / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusKm * c
    }

    fun formatDistance(distanceKm: Double?): String {
        return distanceKm?.let { String.format("%.1f km away", it) } ?: "Distance unavailable"
    }
}
