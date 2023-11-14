package com.example.funs.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funs.screens.login.Login
import com.example.funs.screens.signup.Signup
import com.example.funs.screens.signup.SignupViewModel

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    var viewModel =  viewModel<SignupViewModel>()

    viewModel.navigateTo = {
         navController.navigate(it)
    }
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            Login(navController = navController)
        }

        composable(route = Screen.SignupScreen.route ) {
             Signup(navController = navController)
        }
    }

}