package com.tuapp.organizadorviajes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.organizadorviajes.presentation.screens.all_trips.AllTripsScreen
import com.tuapp.organizadorviajes.presentation.screens.login.LoginScreen
import com.tuapp.organizadorviajes.presentation.screens.my_trips.MyTripsScreen
import com.tuapp.organizadorviajes.presentation.screens.place_detail.PlaceDetailScreen
import com.tuapp.organizadorviajes.presentation.screens.place_form.PlaceFormScreen
import com.tuapp.organizadorviajes.presentation.screens.trip_detail.TripDetailScreen

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")

    data object AllTrips : AppScreen("all_trips/{username}") {
        const val ARG_USERNAME = "username"
        fun createRoute(username: String) = "all_trips/$username"
    }

    data object MyTrips : AppScreen("my_trips/{username}") {
        const val ARG_USERNAME = "username"
        fun createRoute(username: String) = "my_trips/$username"
    }

    data object TripDetail : AppScreen("trip_detail/{tripId}/{tripName}/{username}") {
        const val ARG_TRIP_ID = "tripId"
        const val ARG_TRIP_NAME = "tripName"
        const val ARG_USERNAME = "username"
        fun createRoute(tripId: String, tripName: String, username: String) =
            "trip_detail/$tripId/$tripName/$username"
    }

    data object PlaceForm : AppScreen("place_form/{tripId}/{placeId}") {
        const val ARG_TRIP_ID = "tripId"
        const val ARG_PLACE_ID = "placeId"
        fun createRoute(tripId: String, placeId: String) = "place_form/$tripId/$placeId"
    }

    data object PlaceDetail : AppScreen("place_detail/{placeId}") {
        const val ARG_PLACE_ID = "placeId"
        fun createRoute(placeId: String) = "place_detail/$placeId"
    }
}



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Login.route
    ) {

        composable(route = AppScreen.Login.route) {
            LoginScreen(
                onLoginSuccess = { username ->
                    navController.navigate(AppScreen.AllTrips.createRoute(username)) {
                        popUpTo(AppScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppScreen.AllTrips.route,
            arguments = listOf(navArgument(AppScreen.AllTrips.ARG_USERNAME) { type = NavType.StringType })
        ) {
            val username = it.arguments?.getString(AppScreen.AllTrips.ARG_USERNAME) ?: ""
            AllTripsScreen(
                username = username,
                onTripClick = { tripId, tripName ->
                    navController.navigate(AppScreen.TripDetail.createRoute(tripId, tripName, username))
                },
                onMyTripsClick = {
                    navController.navigate(AppScreen.MyTrips.createRoute(username))
                }
            )
        }

        composable(
            route = AppScreen.MyTrips.route,
            arguments = listOf(navArgument(AppScreen.MyTrips.ARG_USERNAME) { type = NavType.StringType })
        ) {
            val username = it.arguments?.getString(AppScreen.MyTrips.ARG_USERNAME) ?: ""
            MyTripsScreen(
                username = username,
                onTripClick = { tripId, tripName ->
                    navController.navigate(AppScreen.TripDetail.createRoute(tripId, tripName, username))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = AppScreen.TripDetail.route,
            arguments = listOf(
                navArgument(AppScreen.TripDetail.ARG_TRIP_ID) { type = NavType.StringType },
                navArgument(AppScreen.TripDetail.ARG_TRIP_NAME) { type = NavType.StringType },
                navArgument(AppScreen.TripDetail.ARG_USERNAME) { type = NavType.StringType }
            )
        ) {
            val tripId = it.arguments?.getString(AppScreen.TripDetail.ARG_TRIP_ID) ?: ""
            val tripName = it.arguments?.getString(AppScreen.TripDetail.ARG_TRIP_NAME) ?: ""
            val username = it.arguments?.getString(AppScreen.TripDetail.ARG_USERNAME) ?: ""
            TripDetailScreen(
                tripId = tripId,
                tripName = tripName,
                username = username,
                onPlaceClick = { placeId ->
                    navController.navigate(AppScreen.PlaceDetail.createRoute(placeId))
                },
                onAddPlaceClick = {
                    navController.navigate(AppScreen.PlaceForm.createRoute(tripId, "new"))
                },
                onBack = { navController.popBackStack() },
                onEditPlaceClick = { placeId ->
                    navController.navigate(AppScreen.PlaceForm.createRoute(tripId, placeId))
                }
            )
        }

        composable(
            route = AppScreen.PlaceForm.route,
            arguments = listOf(
                navArgument(AppScreen.PlaceForm.ARG_TRIP_ID) { type = NavType.StringType },
                navArgument(AppScreen.PlaceForm.ARG_PLACE_ID) { type = NavType.StringType }
            )
        ) {
            val tripId = it.arguments?.getString(AppScreen.PlaceForm.ARG_TRIP_ID) ?: ""
            val placeId = it.arguments?.getString(AppScreen.PlaceForm.ARG_PLACE_ID) ?: ""
            PlaceFormScreen(
                tripId = tripId,
                placeId = placeId,
                onSave = { navController.popBackStack() }
            )
        }

        composable(
            route = AppScreen.PlaceDetail.route,
            arguments = listOf(navArgument(AppScreen.PlaceDetail.ARG_PLACE_ID) { type = NavType.StringType })
        ) {
            val placeId = it.arguments?.getString(AppScreen.PlaceDetail.ARG_PLACE_ID) ?: ""
            PlaceDetailScreen(
                placeId = placeId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}