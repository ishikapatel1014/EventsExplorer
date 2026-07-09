package com.ishika.eventsexplorer.domain.model

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val time: String,
    val imageUrl: String,
    val isBookmarked: Boolean = false,
    val distanceKm: Double? = null
)
