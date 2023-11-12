package com.example.funs.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.funs.components.BottomNavBar
import com.example.funs.components.ListMenu
import com.example.funs.navigation.AuthNavigation
import com.example.funs.navigation.MainNavigation


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TheFunApp () {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White) {
        Scaffold (
            bottomBar = {
                BottomNavBar(
                    items = listOf(
                        ListMenu(
                            name = "Home",
                            route = "home",
                            icon = Icons.Rounded.Home
                        ),
                        ListMenu(
                            name = "New order",
                            route = "new_order",
                            icon = Icons.Rounded.AddCircle
                        ),
                        ListMenu(
                            name = "Profile",
                            route = "profile",
                            icon = Icons.Outlined.Person
                        ),
                    ),
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route)
                    }
                )
            }
        ){
           MainNavigation(navController = navController)
        }
    }
}
