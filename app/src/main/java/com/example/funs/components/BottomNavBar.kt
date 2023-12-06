package com.example.funs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.funs.screens.notifications.NotificationViewModel
import com.example.funs.screens.profile.ProfileViewModel


@Composable
fun BottomNavBar(
    items: List<ListMenu>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (ListMenu) -> Unit,
    notificationViewModel: NotificationViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {

    val notificationCount by notificationViewModel.unreadNotifications.observeAsState()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val totalCount by notificationViewModel.totalNotificationCount.observeAsState()

    LaunchedEffect(key1 = totalCount) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            notificationViewModel.getUnreadNotifications(userId!!, tokenWithBearer)
        }
    }

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        println("observable count is $totalCount")
                        if (item.route == "notifications") {
                            totalCount?.let {
                                it
                                if (it > 0) {
                                    BadgedBox(
                                        badge = {
                                            Box(
                                                modifier = Modifier
                                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${it}",
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontSize = 10.sp,
                                                    textAlign = TextAlign.Center,
                                                )
                                            }
                                        }
                                    ) {
                                        Icon(imageVector = item.icon, contentDescription = item.name)
                                    }
                                } else {
                                    Icon(imageVector = item.icon, contentDescription = item.name)
                                    if (selected) Text(
                                        text = item.name,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                            if (selected) Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    }
}


