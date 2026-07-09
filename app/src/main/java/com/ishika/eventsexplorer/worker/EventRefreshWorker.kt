package com.ishika.eventsexplorer.worker

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.ishika.eventsexplorer.data.cache.CacheManager
import com.ishika.eventsexplorer.data.local.AppDatabase
import com.ishika.eventsexplorer.data.remote.JsonEventDataSource
import com.ishika.eventsexplorer.data.repository.EventRepository

class EventRefreshWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "events_database"
            ).build()

            val repository = EventRepository(
                eventDao = database.eventDao(),
                jsonEventDataSource = JsonEventDataSource(applicationContext, Gson()),
                cacheManager = CacheManager(applicationContext)
            )

            repository.refreshEvents(force = true)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
