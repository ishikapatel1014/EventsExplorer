package com.ishika.eventsexplorer.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ishika.eventsexplorer.domain.model.Event
import com.ishika.eventsexplorer.util.DistanceUtil

@Composable
fun EventDetailsScreen(
    event: Event,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = "←",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Text(
                    text = if (event.isBookmarked) "♥" else "♡",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text(event.category) })
                AssistChip(
                    onClick = {},
                    label = { Text(DistanceUtil.formatDistance(event.distanceKm)) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Event Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "📍 ${event.location}")
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "🕒 ${event.time}")
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "About this event",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val uri = Uri.parse("google.navigation:q=${event.latitude},${event.longitude}")
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.google.android.apps.maps")
                    }

                    runCatching { context.startActivity(intent) }
                        .onFailure {
                            val browserUri = Uri.parse(
                                "https://www.google.com/maps/search/?api=1&query=${event.latitude},${event.longitude}"
                            )
                            context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
                        }
                }
            ) {
                Text("Open in Google Maps")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onBookmarkClick
            ) {
                Text(if (event.isBookmarked) "Remove Bookmark" else "Save Bookmark")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
