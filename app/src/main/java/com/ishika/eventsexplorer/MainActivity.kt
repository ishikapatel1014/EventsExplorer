package com.ishika.eventsexplorer

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.ishika.eventsexplorer.data.cache.CacheManager
import com.ishika.eventsexplorer.data.local.AppDatabase
import com.ishika.eventsexplorer.data.remote.JsonEventDataSource
import com.ishika.eventsexplorer.data.repository.EventRepository
import com.ishika.eventsexplorer.presentation.home.HomeViewModel
import com.ishika.eventsexplorer.presentation.home.HomeViewModelFactory
import com.ishika.eventsexplorer.presentation.navigation.AppNavigation
import com.ishika.eventsexplorer.ui.theme.EventsExplorerTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "events_database"
        ).build()
    }

    private val repository by lazy {
        EventRepository(
            eventDao = database.eventDao(),
            jsonEventDataSource = JsonEventDataSource(applicationContext, Gson()),
            cacheManager = CacheManager(applicationContext)
        )
    }

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private var homeViewModelRef: HomeViewModel? = null

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) loadLastKnownLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(repository)
            )
            homeViewModelRef = homeViewModel

            EventsExplorerTheme {
                AppNavigation(homeViewModel = homeViewModel)
            }
        }

        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun loadLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                homeViewModelRef?.updateUserLocation(it.latitude, it.longitude)
            }
        }
    }
}
