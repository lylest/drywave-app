package com.example.funs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funs.screens.Login
import com.example.funs.screens.Signup

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            Login(navController = navController)
        }

        composable(route = Screen.SignupScreen.route ) {
             Signup(navController = navController)
        }
    }

}