package com.example.funs.navigation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
    object HomeScreen: Screen("home")
    object OrderScreen: Screen("orders")
    object ProfileScreen: Screen("profile")
    object NewOrder: Screen("new_order")

    object  ViewOrder: Screen("view_order")

    object Notifications: Screen("notifications")
}