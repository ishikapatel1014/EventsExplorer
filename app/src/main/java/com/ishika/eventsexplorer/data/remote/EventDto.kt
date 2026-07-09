package com.ishika.eventsexplorer.data.remote

data class EventDto(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val time: String,
    val imageUrl: String
)
