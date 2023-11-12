package com.example.funs.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun ClickableText(label: String) {
    val annotatedText = buildAnnotatedString {
        append(label)
    }
    ClickableText(text = annotatedText, onClick = {})
}