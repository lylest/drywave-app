package com.example.funs.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.funs.components.SearchInput

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders (navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(text = "Orders",
                        style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ))
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(50.dp),

            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )  {}

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.heightIn(70.dp))
        SearchInput()

        val scrollStateOrder = rememberScrollState()
        Column (
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollStateOrder)
        ) {
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
            OrderCard()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }

}
