package com.ishika.eventsexplorer.presentation.navigation

object Routes {
    const val HOME = "home"
    const val BOOKMARKS = "bookmarks"
    const val DETAILS = "details/{eventId}"

    fun details(eventId: Int): String = "details/$eventId"
}
