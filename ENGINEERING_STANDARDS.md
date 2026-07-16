# 🛠 Engineering Standards

## Overview

This document defines the engineering principles followed in EventsExplorer to ensure maintainable, scalable, and production-ready code.

---

# Coding Standards

The project follows the official **Kotlin Coding Conventions**.

Key principles include:

-   Use meaningful class, function, and variable names.
-   Keep functions small and focused on a single responsibility.
-   Prefer immutable (`val`) over mutable (`var`) whenever possible.
-   Avoid duplicate code (DRY Principle).
-   Keep business logic separate from UI code.
-   Use descriptive comments only where necessary.

---

# Architecture

The application follows:

-   MVVM (Model--View--ViewModel)
-   Repository Pattern
-   Separation of concerns
-   Manual dependency management using a ViewModel Factory

The Repository acts as the single access point for event data while the
ViewModel exposes observable UI state to the Compose screens.
---

# UI Guidelines

The user interface follows modern Android recommendations:

-   Built entirely with Jetpack Compose
-   Material Design 3 components
-   Reusable composables
-   State hoisting where appropriate
-   Responsive layouts
-   Minimal UI logic inside composables

---

# State Management

Application state is managed using:

-   ViewModel
-   Kotlin StateFlow
-   Kotlin Flow
-   Lifecycle-aware state collection

Compose automatically updates the UI whenever observable state changes.

---

# Data Management

The project uses:

-   Room Database for local persistence
-   Repository Pattern for data access
-   SharedPreferences for cache metadata
-   Local JSON file (`assets/events.json`) as the data source

Room serves as the application's persistent source of truth.

---

# Caching Strategy

## Event Cache

-   Stores the last refresh timestamp in SharedPreferences.
-   Uses a 10-minute Time-To-Live (TTL).
-   Reuses Room data when the cache is valid.

## Image Cache

-   Coil automatically provides memory and disk caching for event
    images.

---

# Background Processing

The application includes a WorkManager worker implementation for
refreshing event data.

The worker is implemented but is not periodically scheduled, as periodic
synchronization was outside the scope of the assignment.

---

# Location Services

The application follows Android best practices by:

-   Requesting runtime location permission
-   Using the Fused Location Provider
-   Retrieving the device's last known location
-   Calculating event distance efficiently
-   Opening event locations using a Google Maps deep link

---

# Error Handling

The application handles:

-   Missing location permission
-   Empty event lists
-   Invalid or expired cache
-   Database operations

Because the application uses a bundled JSON data source, network-related
error handling is not required in the current implementation.

---

# Performance Considerations

The project includes several optimizations:

-   Room persistence
-   SharedPreferences TTL cache
-   Coil image caching
-   LazyColumn for efficient list rendering
-   Kotlin Coroutines for asynchronous work
-   StateFlow for reactive UI updates
-   WorkManager-compatible background refresh logic

---

# Testing

The project contains unit tests covering core business logic:

-   Cache policy
-   Bookmark policy
-   Distance calculation

The architecture also supports future Repository, ViewModel, and Compose
UI tests.

---

# Git Workflow

Recommended development workflow:

1.  Create a feature branch.
2.  Implement one logical feature at a time.
3.  Commit small, meaningful changes.
4.  Write clear commit messages.
5.  Merge changes after review.
---

# Example Commit Messages

``` text
Add bookmark functionality

Implement Room persistence

Add cache expiration logic

Improve event detail screen

Update project documentation
```
---

# Security

The project follows basic Android security practices:

-   No API keys or secrets are committed.
-   Runtime permissions are requested only when required.
-   User location is accessed only after permission is granted.
-   Sensitive data is not stored unnecessarily.

---

# Future Improvements

Potential production enhancements include:

-   Replace the local JSON data source with a REST API
-   Introduce Hilt for dependency injection
-   Add CI/CD for automated testing
-   Add Compose UI tests
-   Add repository integration tests
-   Schedule periodic WorkManager refresh
-   Add search and distance filtering
-   Support push notifications
---

# Summary

The project prioritizes clean code, maintainability, offline
persistence, and efficient resource usage while remaining aligned with
the assignment scope.
