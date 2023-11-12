package com.example.funs.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@Composable
fun NormalText(value: String, fontWeight: FontWeight, fontSize: Int, color: Color, alignment: TextAlign, padding: Int ) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn().padding(padding.dp),
        style = TextStyle(
            textAlign = alignment,
            fontWeight = fontWeight,
            fontSize = TextUnit(fontSize.toFloat(), TextUnitType.Sp)
        ),
        color = color
    )
}


@Composable
fun Heading(value: String, fontSize: Int, color: Color, alignment: TextAlign ) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(),
        style = TextStyle(
            textAlign = alignment,
            fontSize = TextUnit(fontSize.toFloat(), TextUnitType.Sp)
        ),
        color = color
    )
}
