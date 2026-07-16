# 🏗️ EventsExplorer Architecture

## Overview

EventsExplorer follows the **MVVM (Model-View-ViewModel)** architecture combined with the **Repository Pattern** to create a clean, maintainable, and testable application.

The application separates UI, business logic, and data access into independent layers while following Google's recommended Android architecture.

---

# Architecture Diagram

```text
                Jetpack Compose UI
                       │
                       ▼
                  HomeViewModel
                       │
                       ▼
                 EventRepository
              ┌────────┴────────┐
              ▼                 ▼
       Room Database     JSON Data Source
       (Persistence)    (assets/events.json)
              │
              ▼
      Cache Manager (TTL)
      SharedPreferences
```

---

# Project Structure

```text
app
│
├── assets
│   └── events.json
│
├── data
│   ├── cache
│   ├── local
│   ├── mapper
│   ├── remote
│   └── repository
│
├── domain
│
├── presentation
│   ├── home
│   ├── details
│   ├── bookmarks
│   ├── components
│   └── navigation
│
├── util
│
├── worker
│
└── MainActivity.kt
```

---

# MVVM Flow

```text
User
 │
 ▼
Compose Screen
 │
 ▼
ViewModel
 │
 ▼
Repository
 │
 ├──────────────┐
 ▼              ▼
Room      JSON Data Source
 │
 ▼
StateFlow
 │
 ▼
Compose UI
```

---

# Data Flow

1. User opens the application.
2. The ViewModel requests data from the Repository.
3. The Repository checks the cache expiration.
4. If the cache is valid, Room data is used.
5. Otherwise, data is loaded from the bundled `events.json` file.
6. Events are stored in Room.
7. Room emits updates through Flow.
8. StateFlow updates the Compose UI automatically.

---

# Design Decisions

## MVVM

- Separates UI from business logic
- Easier testing
- Lifecycle-aware

## Repository Pattern

- Single source of truth
- Centralizes data access
- Simplifies future expansion

## Room Database

- Persists events locally
- Stores bookmark state
- Supports offline access

## Local JSON Data Source

The assignment allowed either a mock REST API or a JSON file.

A bundled JSON file was selected to keep the project deterministic and avoid external dependencies during the time-boxed implementation.

The data source can easily be replaced by Retrofit in the future.

## Manual Dependency Management

Dependencies are created manually using a ViewModel Factory.

For a larger production application, Hilt could be introduced to improve dependency management.

## Kotlin Coroutines & Flow

- Asynchronous operations
- Reactive UI updates
- Non-blocking execution

---

# Scalability

The architecture allows future additions with minimal changes, including:

- REST API integration
- Search
- Distance filtering
- Authentication
- Push notifications
- Calendar synchronization
- Ticket booking
- Analytics

---

# Error Handling

The application handles:

- Missing location permission
- Empty event list
- Invalid cache
- Database operations

The current implementation does not include network error handling because the application uses a bundled local JSON data source.

---

# Performance Optimizations

- Room persistence
- Coil image caching
- SharedPreferences TTL cache
- LazyColumn rendering
- Kotlin Coroutines
- StateFlow
- WorkManager worker implementation

---

# Testing Strategy

The project includes unit tests for:

- Cache policy
- Bookmark policy
- Distance calculation

The architecture supports additional Repository, ViewModel, and UI tests as the application grows.

---

# Future Improvements

- Replace JSON with Retrofit
- Hilt Dependency Injection
- Pull-to-refresh
- Search
- Distance filtering
- Periodic WorkManager scheduling
- Compose UI tests
- CI/CD
- Firebase
- Push notifications

---

# Summary

EventsExplorer follows a modern Android architecture using **MVVM**, the **Repository Pattern**, **Room**, **Jetpack Compose**, **Coroutines**, **Flow**, and **WorkManager**.

The project emphasizes maintainability, clear separation of concerns, offline persistence, and efficient resource usage while remaining aligned with the assignment scope.
