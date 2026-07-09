package com.ishika.eventsexplorer.domain

import com.ishika.eventsexplorer.domain.model.Event

object BookmarkPolicy {

    fun toggle(event: Event): Event {
        return event.copy(
            isBookmarked = !event.isBookmarked
        )
    }
}