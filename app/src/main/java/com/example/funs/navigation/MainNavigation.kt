package com.example.funs.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funs.screens.*

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