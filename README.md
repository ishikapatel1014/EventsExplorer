# 📅 EventsExplorer

A modern Android application built using **Kotlin** and **Jetpack Compose** that helps users discover, explore, and bookmark events. The application follows **MVVM Architecture** and leverages modern Android development practices to deliver a responsive, scalable, and user-friendly experience.

---

## 🚀 Features

- 📋 Browse upcoming events
- 📄 View event details
- ❤️ Bookmark favorite events
- 💾 Persistent bookmarks using Room Database
- 📍 Calculate distance to events using device location
- 🗺️ Open event location in Google Maps
- 🖼️ Image caching using Coil
- ⚡ Local event caching with 10-minute TTL
- 📱 Responsive UI built with Jetpack Compose
- 🔄 Background refresh worker implementation
- 📦 Offline access to previously stored events

---

## 📱 Screens

- Home
- Event Details
- Bookmarks

---

## 🛠 Tech Stack

## Language
- Kotlin

## UI
- Jetpack Compose
- Material Design 3

## Architecture
- MVVM
- Repository Pattern

## Local Storage
- Room Database
- SharedPreferences (Cache Metadata)

## Data Source
- Local JSON (assets/events.json)
- Gson

## Asynchronous Programming
- Kotlin Coroutines
- Flow
- StateFlow

## Navigation
- Navigation Compose

## Location
- Fused Location Provider

## Background Processing
- WorkManager

## Image Loading
- Coil

## Testing
- JUnit4

---

# 💾 Caching Strategy

### Event Cache

The application stores the last refresh timestamp using **SharedPreferences**.

If cached data is less than **10 minutes old**, Room data is reused instead of reloading the JSON source.

### Image Cache

Images are automatically cached by **Coil**, reducing memory and network usage.

---

# 📍 Native Android Features

- Runtime location permission
- Distance calculation using current location
- Google Maps deep link
- Background refresh worker

---

## 📂 Project Structure

```
app/
├── assets/
│   └── events.json
│
├── data/
│   ├── cache/
│   ├── local/
│   ├── mapper/
│   ├── remote/
│   └── repository/
│
├── domain/
│
├── presentation/
│   ├── home/
│   ├── details/
│   ├── bookmarks/
│   └── components/
│
├── util/
│
├── worker/
│
└── MainActivity.kt
```

---

## ⚙️ Getting Started


## Requirements

- Android Studio Narwhal or newer
- Android SDK 36
- Java 11
- Kotlin 2.2+

## Clone Repository

```bash
git clone https://github.com/ishikapatel1014/EventsExplorer.git
```

## Run

1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the application.
4. Grant location permission.

No API key is required because event data is loaded from a bundled JSON file.

---

## 🧪 Testing

The project includes unit tests for:

- Cache policy
- Bookmark policy
- Distance calculation

Run tests:

```bash
./gradlew test
```

---

## 📈 Future Improvements

- Replace JSON with Retrofit REST API
- Schedule periodic WorkManager refresh
- Add pull-to-refresh
- Search events
- Filter by distance
- Embedded Google Maps
- Compose UI tests
- Repository integration tests
- Hilt Dependency Injection
- CI/CD pipeline

---

## 📚 Libraries Used

- Jetpack Compose
- Material 3
- Room
- Navigation Compose
- Coil
- Coroutines
- Flow
- WorkManager
- Gson
- Google Play Services Location

---

## 👩‍💻 Author

**Ishika Patel**

Android Developer | Kotlin | Java | Jetpack Compose | MVVM

GitHub: https://github.com/ishikapatel1014

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!
