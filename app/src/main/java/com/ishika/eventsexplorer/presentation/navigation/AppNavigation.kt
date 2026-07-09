package com.ishika.eventsexplorer.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ishika.eventsexplorer.presentation.bookmarks.BookmarksScreen
import com.ishika.eventsexplorer.presentation.details.EventDetailsScreen
import com.ishika.eventsexplorer.presentation.home.HomeScreen
import com.ishika.eventsexplorer.presentation.home.HomeViewModel

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: String
)

@Composable
fun AppNavigation(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val selectedEvent by homeViewModel.selectedEvent.collectAsState()

    val bottomItems = listOf(
        BottomNavItem(Routes.HOME, "Home", "⌂"),
        BottomNavItem(Routes.BOOKMARKS, "Bookmarks", "♥")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = bottomItems.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(item.icon) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onEventClick = { eventId ->
                        homeViewModel.loadEvent(eventId)
                        navController.navigate(Routes.details(eventId))
                    }
                )
            }

            composable(Routes.BOOKMARKS) {
                BookmarksScreen(
                    viewModel = homeViewModel,
                    onEventClick = { eventId ->
                        homeViewModel.loadEvent(eventId)
                        navController.navigate(Routes.details(eventId))
                    }
                )
            }

            composable(
                route = Routes.DETAILS,
                arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable

                LaunchedEffect(eventId) {
                    homeViewModel.loadEvent(eventId)
                }

                val event = selectedEvent
                if (event != null) {
                    EventDetailsScreen(
                        event = event,
                        onBackClick = {
                            homeViewModel.clearSelectedEvent()
                            navController.popBackStack()
                        },
                        onBookmarkClick = { homeViewModel.toggleBookmark(event) }
                    )
                } else {
                    Text("Loading event details...")
                }
            }
        }
    }
}
