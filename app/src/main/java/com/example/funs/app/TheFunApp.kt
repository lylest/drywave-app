package com.example.funs.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.funs.components.BottomNavBar
import com.example.funs.components.ListMenu
import com.example.funs.navigation.AuthNavigation
import com.example.funs.navigation.MainNavigation
import com.example.funs.screens.signup.SignupViewModel


@Composable
fun TheFunApp(appViewModel: AppViewModel = viewModel()) {
    val userId by appViewModel.userId.observeAsState()
    val currentUserToken by appViewModel.currentUserToken.observeAsState()

    if (currentUserToken != null && currentUserToken != "not-found") {
        MainNav()
    } else if (currentUserToken == "not-found") {
        AuthNavigation()
    } else {
        AuthNavigation()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNav () {
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
                            name = "Notifications",
                            route = "notifications",
                            icon = Icons.Rounded.Notifications
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
