package com.ishika.eventsexplorer.presentation.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ishika.eventsexplorer.presentation.components.EmptyState
import com.ishika.eventsexplorer.presentation.components.EventCard
import com.ishika.eventsexplorer.presentation.home.HomeViewModel

@Composable
fun BookmarksScreen(
    viewModel: HomeViewModel,
    onEventClick: (Int) -> Unit
) {
    val bookmarkedEvents by viewModel.bookmarkedEvents.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Bookmarked Events",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        if (bookmarkedEvents.isEmpty()) {
            EmptyState(
                title = "No bookmarks yet",
                message = "Tap the heart icon on any event to save it here."
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 96.dp)
            ) {
                items(
                    items = bookmarkedEvents,
                    key = { it.id }
                ) { event ->
                    EventCard(
                        event = event,
                        onClick = { onEventClick(event.id) },
                        onBookmarkClick = { viewModel.toggleBookmark(event) }
                    )
                }
            }
        }
    }
}
