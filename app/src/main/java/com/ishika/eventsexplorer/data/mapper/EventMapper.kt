package com.ishika.eventsexplorer.data.mapper

import com.ishika.eventsexplorer.data.local.EventEntity
import com.ishika.eventsexplorer.data.remote.EventDto
import com.ishika.eventsexplorer.domain.model.Event

fun EventEntity.toDomain(distanceKm: Double? = null): Event = Event(
    id = id,
    title = title,
    description = description,
    category = category,
    location = location,
    latitude = latitude,
    longitude = longitude,
    time = time,
    imageUrl = imageUrl,
    isBookmarked = isBookmarked,
    distanceKm = distanceKm
)

fun Event.toEntity(): EventEntity = EventEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    location = location,
    latitude = latitude,
    longitude = longitude,
    time = time,
    imageUrl = imageUrl,
    isBookmarked = isBookmarked
)

fun EventDto.toEntity(): EventEntity = EventEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    location = location,
    latitude = latitude,
    longitude = longitude,
    time = time,
    imageUrl = imageUrl,
    isBookmarked = false
)
