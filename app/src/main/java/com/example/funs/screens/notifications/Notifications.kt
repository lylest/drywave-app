package com.example.funs.screens.notifications

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.funs.components.EmptyData
import com.example.funs.screens.profile.IndeterminateCircularIndicator
import com.example.funs.screens.profile.ProfileViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Notifications(
    navController: NavController,
    notificationViewModel: NotificationViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val scrollStateOrder = rememberScrollState()
    val userId by profileViewModel.userId.observeAsState()
    val currentUserToken by profileViewModel.currentUserToken.observeAsState()
    val notificationsList by notificationViewModel.notifications.observeAsState()

    LaunchedEffect(key1 = userId) {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            notificationViewModel.getNotifications(userId!!, tokenWithBearer)
            notificationViewModel.getUnreadNotifications(userId!!,tokenWithBearer)
        }
    }

    val context = LocalContext.current
    if (notificationViewModel.showToast.value) {
        LaunchedEffect(key1 = 1) {
            Toast.makeText(context, notificationViewModel.message.value, Toast.LENGTH_SHORT).show()
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing = notificationViewModel.refreshing.value

    fun refresh() = refreshScope.launch {
        if (userId != null && currentUserToken != null) {
            val tokenWithBearer = "Bearer $currentUserToken"
            notificationViewModel.getNotifications(userId!!, tokenWithBearer)
            notificationViewModel.getUnreadNotifications(userId!!, tokenWithBearer)
        }
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    Box(Modifier.pullRefresh(state)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollStateOrder)
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "Notifications",
                        modifier = Modifier.fillMaxWidth().heightIn().padding(top = 15.dp, start = 24.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            when {
                notificationViewModel.isLoading.value -> {
                    IndeterminateCircularIndicator()
                }

                notificationsList.isNullOrEmpty() -> {
                    EmptyData(Icons.Outlined.Notifications, "No, Notifications")
                }

                else -> {
                    notificationsList?.forEach { notification ->
                        NotiBar({
                            val tokenWithBearer = "Bearer $currentUserToken"
                            notificationViewModel.markAsRead(tokenWithBearer, notification._id)
                            refresh()
                        }, notification)
                    }
                }
            }

            Spacer(modifier = Modifier.height(90.dp))
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun NotiBar(handleClickable: () -> Unit, notification: Notification) {
    fun timeAgo(dateString: String): String {
        val utc = TimeZone.getTimeZone("UTC")
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        formatter.timeZone = utc
        val pastDate = formatter.parse(dateString)
        val now = Date()

        // Convert both times to milliseconds since the epoch, in UTC
        val pastTime = pastDate.time
        val nowTime = now.time

        val diff = nowTime - pastTime

        val years = TimeUnit.MILLISECONDS.toDays(diff) / 365
        if (years > 0) return "$years years ago"

        val months = TimeUnit.MILLISECONDS.toDays(diff) / 30
        if (months > 0) return "$months months ago"

        val weeks = TimeUnit.MILLISECONDS.toDays(diff) / 7
        if (weeks > 0) return "$weeks weeks ago"

        val days = TimeUnit.MILLISECONDS.toDays(diff)
        if (days > 0) return "$days days ago"

        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        if (hours > 0) return "$hours hrs ago"

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        if (minutes > 0) return "$minutes min ago"

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        return "$seconds sec ago"
    }


    Column(modifier = Modifier.fillMaxWidth(0.9f).padding(top = 10.dp, bottom = 0.dp, start = 20.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                tint = if (notification.isRead) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.primary
                },
                contentDescription = null,
                modifier = Modifier.padding(top = 12.dp, end = 0.dp, bottom = 0.dp, start = 0.dp)
            )


            Text(
                text = notification.title,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(
                        top = 12.dp, bottom = 0.dp, start = 12.dp, end = 12.dp
                    ),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                color = if (notification.isRead) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            Text(
                text = timeAgo(notification.createdAt),
                modifier = Modifier.padding(top = 17.dp, bottom = 0.dp, start = 12.dp, end = 12.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                ),
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = notification.body,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 10.dp, bottom = 12.dp, start = 10.dp, end = 5.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                color = if (notification.isRead) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable {
                            handleClickable()
                        }
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DoneAll,
                        contentDescription = "Read",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

        Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
    }
}
