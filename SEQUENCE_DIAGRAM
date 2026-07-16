# 🔄 EventsExplorer Sequence Diagrams

## Overview

This document describes the main interaction flows in **EventsExplorer**.

The diagrams reflect the current implementation, which uses:

- Jetpack Compose
- MVVM
- `HomeViewModel`
- `EventRepository`
- Room Database
- Local JSON data from `assets/events.json`
- SharedPreferences for cache metadata
- Kotlin Coroutines and Flow
- Fused Location Provider
- Google Maps deep links
- WorkManager worker implementation

---

# 1. Application Launch and Event Loading

```mermaid
sequenceDiagram
    actor User
    participant UI as Compose UI
    participant VM as HomeViewModel
    participant Repo as EventRepository
    participant Cache as CacheManager
    participant DB as Room Database
    participant JSON as JSON Data Source

    User->>UI: Launch application
    UI->>VM: Initialize screen
    VM->>Repo: Observe events()
    Repo->>DB: Observe stored events with Flow
    DB-->>Repo: Emit stored events
    Repo-->>VM: Emit event list
    VM-->>UI: Update StateFlow
    UI-->>User: Display cached events

    VM->>Repo: refreshEvents()
    Repo->>DB: Check whether events exist
    DB-->>Repo: Return event count
    Repo->>Cache: Check 10-minute TTL
    Cache-->>Repo: Return cache status

    alt Cache valid and Room contains events
        Repo-->>VM: Keep existing Room data
    else Cache expired or Room is empty
        Repo->>JSON: Read assets/events.json
        JSON-->>Repo: Return event DTOs
        Repo->>DB: Read existing bookmark states
        DB-->>Repo: Return bookmark states
        Repo->>Repo: Map DTOs to entities
        Repo->>DB: Insert or replace events
        Repo->>Cache: Save refresh timestamp
        DB-->>Repo: Emit updated events
        Repo-->>VM: Emit updated event list
        VM-->>UI: Update StateFlow
        UI-->>User: Display refreshed events
    end
```

---

# 2. Bookmark Event Flow

```mermaid
sequenceDiagram
    actor User
    participant UI as Compose UI
    participant VM as HomeViewModel
    participant Repo as EventRepository
    participant Policy as BookmarkPolicy
    participant DB as Room Database

    User->>UI: Tap bookmark icon
    UI->>VM: toggleBookmark(event)
    VM->>Policy: Calculate new bookmark state
    Policy-->>VM: Return toggled state
    VM->>Repo: Update bookmark
    Repo->>DB: Persist bookmark state
    DB-->>Repo: Emit updated events through Flow
    Repo-->>VM: Emit updated event list
    VM-->>UI: Update StateFlow
    UI-->>User: Show updated bookmark icon
```

---

# 3. Bookmarks Screen Flow

```mermaid
sequenceDiagram
    actor User
    participant UI as Bookmarks Screen
    participant VM as HomeViewModel
    participant Repo as EventRepository
    participant DB as Room Database

    User->>UI: Open Bookmarks screen
    UI->>VM: Observe bookmarked events
    VM->>Repo: Request bookmarked events
    Repo->>DB: Query bookmarked events
    DB-->>Repo: Emit bookmarked event list
    Repo-->>VM: Return bookmarked events
    VM-->>UI: Update screen state

    alt Bookmarks exist
        UI-->>User: Display bookmarked events
    else No bookmarks exist
        UI-->>User: Display empty state
    end
```

---

# 4. Event Details Flow

```mermaid
sequenceDiagram
    actor User
    participant Home as Home Screen
    participant Nav as Navigation Compose
    participant Details as Event Details Screen
    participant VM as HomeViewModel
    participant Repo as EventRepository
    participant DB as Room Database

    User->>Home: Select an event
    Home->>Nav: Navigate with event ID
    Nav->>Details: Open details destination
    Details->>VM: Request event by ID
    VM->>Repo: getEventById(eventId)
    Repo->>DB: Query event
    DB-->>Repo: Return event
    Repo-->>VM: Return domain model
    VM-->>Details: Update event state
    Details-->>User: Display event information
```

---

# 5. Location and Distance Calculation Flow

```mermaid
sequenceDiagram
    actor User
    participant UI as Compose UI
    participant Permission as Android Permission System
    participant Location as Fused Location Provider
    participant VM as HomeViewModel
    participant Distance as DistanceUtil

    UI->>Permission: Check location permission

    alt Permission not granted
        UI->>User: Request location permission
        User->>Permission: Grant or deny permission
        Permission-->>UI: Return permission result
    end

    alt Permission granted
        UI->>Location: Request last known location
        Location-->>UI: Return location or null
        UI->>VM: Submit user coordinates
        VM->>Distance: Calculate distance to each event
        Distance-->>VM: Return distance values
        VM-->>UI: Update event state
        UI-->>User: Display distance in kilometres
    else Permission denied or location unavailable
        UI-->>User: Display "Distance unavailable"
    end
```

---

# 6. Open Event in Google Maps

```mermaid
sequenceDiagram
    actor User
    participant Details as Event Details Screen
    participant Android as Android Intent System
    participant Maps as Google Maps App
    participant Browser as Web Browser

    User->>Details: Tap navigation action
    Details->>Android: Create maps intent with coordinates

    alt Google Maps app is available
        Android->>Maps: Open event location
        Maps-->>User: Display route or location
    else Google Maps app is unavailable
        Details->>Android: Create browser fallback intent
        Android->>Browser: Open Google Maps website
        Browser-->>User: Display event location
    end
```

---

# 7. WorkManager Refresh Worker

```mermaid
sequenceDiagram
    participant WM as WorkManager
    participant Worker as EventRefreshWorker
    participant Repo as EventRepository
    participant JSON as JSON Data Source
    participant DB as Room Database
    participant Cache as CacheManager

    Note over WM,Worker: Worker implementation exists, but periodic scheduling is not configured.

    WM->>Worker: Execute background work
    Worker->>Repo: Force event refresh
    Repo->>JSON: Read assets/events.json
    JSON-->>Repo: Return event DTOs
    Repo->>DB: Preserve bookmark states
    DB-->>Repo: Return existing bookmark states
    Repo->>DB: Insert refreshed events
    Repo->>Cache: Update refresh timestamp
    Repo-->>Worker: Refresh completed

    alt Refresh succeeds
        Worker-->>WM: Result.success()
    else Exception occurs
        Worker-->>WM: Result.retry()
    end
```

---

# Notes

- The application currently uses a bundled JSON file rather than a remote REST API.
- Room acts as the persistent source of event and bookmark data.
- SharedPreferences stores the last refresh timestamp for the 10-minute TTL.
- Location access uses the device's last known location to reduce battery consumption.
- Google Maps is opened through a deep link rather than an embedded map.
- The WorkManager worker is implemented, but periodic scheduling is outside the current assignment scope.
