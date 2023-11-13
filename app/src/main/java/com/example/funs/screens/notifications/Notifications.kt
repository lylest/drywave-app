package com.example.funs.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Notifications (navController: NavController) {
    val scrollStateOrder = rememberScrollState()
    Column() {
        Box(modifier = Modifier
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollStateOrder)
        ) {

            Spacer(modifier = Modifier.height(15.dp))
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            NotiBar()
            Spacer(modifier = Modifier.height(90.dp))
        }
    }

}

@Composable
fun NotiBar () {
    Column (modifier = Modifier.fillMaxWidth(0.9f).padding(top = 10.dp, bottom = 0.dp, start=  20.dp )) {
        Row() {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.padding(top = 12.dp, end = 0.dp, bottom = 0.dp, start = 0.dp)
            )

            Text(
                text = "New order place",
                modifier = Modifier.padding( top = 12.dp, bottom = 0.dp, start = 12.dp, end = 12.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "2 minutes ago",
                modifier = Modifier.padding( top = 17.dp, bottom = 0.dp, start = 12.dp, end = 12.dp),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                ),
                color = MaterialTheme.colorScheme.outline
            )
        }
        Text(
            text = "This should give you the desired result of a thicker and dotted line. I appreciate your patience and understanding.",
            modifier = Modifier.padding( top = 10.dp, bottom = 12.dp, start = 10.dp, end = 12.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(MaterialTheme.colorScheme.surface))
    }
}
