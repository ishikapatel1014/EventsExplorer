package com.ishika.eventsexplorer.data.repository

import com.ishika.eventsexplorer.data.cache.CacheManager
import com.ishika.eventsexplorer.data.local.EventDao
import com.ishika.eventsexplorer.data.mapper.toDomain
import com.ishika.eventsexplorer.data.mapper.toEntity
import com.ishika.eventsexplorer.data.remote.JsonEventDataSource
import com.ishika.eventsexplorer.domain.BookmarkPolicy
import com.ishika.eventsexplorer.domain.model.Event
import com.ishika.eventsexplorer.util.DistanceUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val eventDao: EventDao,
    private val jsonEventDataSource: JsonEventDataSource,
    private val cacheManager: CacheManager
) {
    fun getEvents(userLat: Double? = null, userLng: Double? = null): Flow<List<Event>> {
        return eventDao.getEvents().map { entities ->
            entities.map { entity ->
                entity.toDomain(
                    distanceKm = calculateDistance(
                        userLat,
                        userLng,
                        entity.latitude,
                        entity.longitude
                    )
                )
            }
        }
    }

    fun getBookmarkedEvents(userLat: Double? = null, userLng: Double? = null): Flow<List<Event>> {
        return eventDao.getBookmarkedEvents().map { entities ->
            entities.map { entity ->
                entity.toDomain(
                    distanceKm = calculateDistance(
                        userLat,
                        userLng,
                        entity.latitude,
                        entity.longitude
                    )
                )
            }
        }
    }

    suspend fun getEventById(
        id: Int,
        userLat: Double? = null,
        userLng: Double? = null
    ): Event? {
        val entity = eventDao.getEventById(id) ?: return null
        return entity.toDomain(
            distanceKm = calculateDistance(userLat, userLng, entity.latitude, entity.longitude)
        )
    }

    suspend fun refreshEvents(force: Boolean = false) {
        if (!force && cacheManager.isCacheValid() && eventDao.getEventsOnce().isNotEmpty()) return

        val existingEvents = eventDao.getEventsOnce()
        val bookmarkMap = existingEvents.associate { it.id to it.isBookmarked }

        val events = jsonEventDataSource.loadEvents().map { dto ->
            dto.toEntity().copy(isBookmarked = bookmarkMap[dto.id] ?: false)
        }

        eventDao.insertEvents(events)
        cacheManager.updateLastRefresh()
    }

    suspend fun toggleBookmark(event: Event) {
        eventDao.updateEvent(
            BookmarkPolicy.toggle(event).toEntity()
        )
    }

    private fun calculateDistance(
        userLat: Double?,
        userLng: Double?,
        eventLat: Double,
        eventLng: Double
    ): Double? {
        return if (userLat != null && userLng != null) {
            DistanceUtil.calculateDistanceKm(userLat, userLng, eventLat, eventLng)
        } else {
            null
        }
    }
}
