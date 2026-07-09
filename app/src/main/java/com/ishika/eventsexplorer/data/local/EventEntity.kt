package com.ishika.eventsexplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val time: String,
    val imageUrl: String,
    val isBookmarked: Boolean = false
)
