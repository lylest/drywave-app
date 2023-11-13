package com.example.funs.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonComponent(label: String, onButtonClicked: () -> Unit) {
    Button(
        onClick = { onButtonClicked.invoke() },
        modifier = Modifier.fillMaxWidth(0.8f).heightIn(50.dp),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues()
    ) {

        Box(
            modifier = Modifier
                .heightIn(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ButtonAdd(label: String) {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth().heightIn(50.dp),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues()
    ) {

        Box(
            modifier = Modifier
                .heightIn(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}