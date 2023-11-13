package com.example.funs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.funs.screens.home.Home
import com.example.funs.screens.neworder.NewOrder
import com.example.funs.screens.notifications.Notifications
import com.example.funs.screens.profile.Profile
import com.example.funs.screens.vieworder.ViewOrder

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            Home(navController = navController)
        }

        composable(route = Screen.ProfileScreen.route) {
            Profile(navController = navController)
        }

        composable(route = Screen.NewOrder.route) {
            NewOrder(navController = navController)
        }

        composable(route = Screen.ViewOrder.route) {
            ViewOrder(navController = navController)
        }

        composable(route =Screen.Notifications.route) {
            Notifications(navController = navController)
        }

    }

}