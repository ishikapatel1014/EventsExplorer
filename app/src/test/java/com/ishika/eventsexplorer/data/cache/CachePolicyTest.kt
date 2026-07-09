package com.ishika.eventsexplorer.data.cache

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CachePolicyTest {

    @Test
    fun cacheIsValid_whenWithinTtl() {
        val currentTime = 1_000_000L
        val lastRefresh = currentTime - 5 * 60 * 1000L

        val result = CachePolicy.isCacheValid(
            lastRefreshTime = lastRefresh,
            currentTime = currentTime
        )

        assertTrue(result)
    }

    @Test
    fun cacheIsInvalid_whenOutsideTtl() {
        val currentTime = 1_000_000L
        val lastRefresh = currentTime - 11 * 60 * 1000L

        val result = CachePolicy.isCacheValid(
            lastRefreshTime = lastRefresh,
            currentTime = currentTime
        )

        assertFalse(result)
    }

    @Test
    fun cacheIsInvalid_whenNeverRefreshed() {
        val result = CachePolicy.isCacheValid(
            lastRefreshTime = 0L,
            currentTime = 1_000_000L
        )

        assertFalse(result)
    }
}