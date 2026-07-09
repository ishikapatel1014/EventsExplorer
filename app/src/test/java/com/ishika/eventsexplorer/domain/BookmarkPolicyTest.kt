package com.ishika.eventsexplorer.domain

import com.ishika.eventsexplorer.domain.model.Event
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BookmarkPolicyTest {

    @Test
    fun toggle_whenEventIsNotBookmarked_returnsBookmarkedEvent() {
        val event = fakeEvent(isBookmarked = false)

        val result = BookmarkPolicy.toggle(event)

        assertTrue(result.isBookmarked)
    }

    @Test
    fun toggle_whenEventIsBookmarked_returnsUnbookmarkedEvent() {
        val event = fakeEvent(isBookmarked = true)

        val result = BookmarkPolicy.toggle(event)

        assertFalse(result.isBookmarked)
    }

    private fun fakeEvent(
        isBookmarked: Boolean
    ): Event {
        return Event(
            id = 1,
            title = "Test Event",
            description = "Test Description",
            category = "Music",
            location = "Toronto",
            latitude = 43.6532,
            longitude = -79.3832,
            time = "2026-07-15 18:00",
            imageUrl = "https://example.com/image.jpg",
            isBookmarked = isBookmarked
        )
    }
}