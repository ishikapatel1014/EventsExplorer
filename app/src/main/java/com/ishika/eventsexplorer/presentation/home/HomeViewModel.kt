package com.ishika.eventsexplorer.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishika.eventsexplorer.data.repository.EventRepository
import com.ishika.eventsexplorer.domain.model.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _userLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    val events = _userLocation.flatMapLatest { location ->
        repository.getEvents(
            userLat = location?.first,
            userLng = location?.second
        )
    }

    val bookmarkedEvents = _userLocation.flatMapLatest { location ->
        repository.getBookmarkedEvents(
            userLat = location?.first,
            userLng = location?.second
        )
    }

    init {
        refreshEvents()
    }

    fun refreshEvents(force: Boolean = false) {
        viewModelScope.launch {
            runCatching { repository.refreshEvents(force) }
        }
    }

    fun updateUserLocation(latitude: Double, longitude: Double) {
        _userLocation.value = latitude to longitude
    }

    fun toggleBookmark(event: Event) {
        viewModelScope.launch {
            repository.toggleBookmark(event)
            _selectedEvent.value = repository.getEventById(
                id = event.id,
                userLat = _userLocation.value?.first,
                userLng = _userLocation.value?.second
            )
        }
    }

    fun loadEvent(eventId: Int) {
        viewModelScope.launch {
            _selectedEvent.value = repository.getEventById(
                id = eventId,
                userLat = _userLocation.value?.first,
                userLng = _userLocation.value?.second
            )
        }
    }

    fun clearSelectedEvent() {
        _selectedEvent.value = null
    }
}
