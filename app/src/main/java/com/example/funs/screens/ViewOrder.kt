package com.example.funs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun ViewOrder (navController: NavController) {
    val scrollStateOrder = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollStateOrder)
    ) {
        Text(
            text = "Order details",
            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 30.dp, start = 24.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "5PC-5973-Mon-WDY",
            modifier = Modifier.fillMaxWidth().heightIn().padding(top = 0.dp, start = 24.dp),
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}