package com.ishika.eventsexplorer.data.cache

import android.content.Context

class CacheManager(
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        "events_cache",
        Context.MODE_PRIVATE
    )

    fun isCacheValid(): Boolean {
        val lastRefresh = prefs.getLong("last_refresh", 0L)

        return CachePolicy.isCacheValid(
            lastRefreshTime = lastRefresh,
            currentTime = System.currentTimeMillis()
        )
    }

    fun updateLastRefresh() {
        prefs.edit()
            .putLong("last_refresh", System.currentTimeMillis())
            .apply()
    }
}