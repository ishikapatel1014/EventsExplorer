package com.ishika.eventsexplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY time ASC")
    fun getEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events")
    suspend fun getEventsOnce(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getEventById(id: Int): EventEntity?

    @Query("SELECT * FROM events WHERE isBookmarked = 1 ORDER BY time ASC")
    fun getBookmarkedEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Query("DELETE FROM events")
    suspend fun clearEvents()
}
