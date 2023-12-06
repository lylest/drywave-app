package com.example.funs.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyData (icon: ImageVector, title:String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                tint = androidx.compose.material3.MaterialTheme.colorScheme.outline,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = title,
                        style = TextStyle(
                        textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            ),
            color = androidx.compose.material3.MaterialTheme.colorScheme.outline
            )
        }
    }
}