package com.ishika.eventsexplorer.data.remote

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonEventDataSource(
    private val context: Context,
    private val gson: Gson
) {
    fun loadEvents(): List<EventDto> {
        val json = context.assets.open("events.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<EventDto>>() {}.type
        val events: List<EventDto> = gson.fromJson(json, type)
        Log.d("EventsExplorer", "Loaded ${events.size} events")
        return events
    }
}
