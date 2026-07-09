package com.ishika.eventsexplorer.data.cache

object CachePolicy {

    const val TTL_MILLIS = 10 * 60 * 1000L

    fun isCacheValid(
        lastRefreshTime: Long,
        currentTime: Long
    ): Boolean {
        return currentTime - lastRefreshTime < TTL_MILLIS
    }
}